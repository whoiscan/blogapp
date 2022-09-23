package com.example.userservice.services.impl;

import com.example.userservice.entities.Role;
import com.example.userservice.entities.User;
import com.example.userservice.models.Response;
import com.example.userservice.models.UserDTO;
import com.example.userservice.models.UserLoginDTO;
import com.example.userservice.models.UserRequestDTO;
import com.example.userservice.repositories.RoleRepository;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Response getByUsername(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUsername(userLoginDTO.getUsername());
        if (user == null)
            return new Response(false, "User not found!", null);
        if (!bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), user.getPassword()))
            return new Response(false, "Bad Credentials!", null);
        return new Response(true, "User found!", parseUserToDto(user));
    }

    @Override
    public Response save(UserRequestDTO userRequestDTO) {
        Role role = roleRepository.findByName(userRequestDTO.getRoleName());
        if (role == null)
            return new Response(false, "Role not found!", null);
        if (userRepository.existsByUsername(userRequestDTO.getUsername()))
            return new Response(false, "Username is already taken!", null);
        User user = userRepository.save(new User(userRequestDTO.getUsername(), userRequestDTO.getFirstname(), userRequestDTO.getLastname(), bCryptPasswordEncoder.encode(userRequestDTO.getPassword()),
                Collections.singleton(role)));
        return new Response(true, "User saved successfully!", parseUserToDto(user));
    }

    @Override
    public Response getById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return new Response(false, "User not found!", null);
        return new Response(parseUserToDto(user), true);
    }

    @Override
    public Response delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return new Response(false, "User not found!", null);
        userRepository.delete(user);
        return new Response(true, "User deleted successfully!", null);
    }

    @Override
    public Response update(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return new Response(false, "User not found!", null);
        user.setUsername(userRequestDTO.getUsername());
        user.setFirstname(userRequestDTO.getFirstname());
        user.setLastname(userRequestDTO.getLastname());
        user.setPassword(userRequestDTO.getPassword());
        userRepository.save(user);
        return new Response(true, "User updated successfully!", null);
    }

    @Override
    public Response getAll() {
        return new Response(userRepository.findAll(), true);
    }

    private UserDTO parseUserToDto(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname());
    }
}
