package com.example.userservice.services.impl;

import com.example.userservice.entities.Role;
import com.example.userservice.entities.User;
import com.example.userservice.models.Response;
import com.example.userservice.models.UserDTO;
import com.example.userservice.models.UserRequestDTO;
import com.example.userservice.repositories.RoleRepository;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Response save(UserRequestDTO userRequestDTO) {
        Role role = roleRepository.findByName(userRequestDTO.getRoleName());
        if (role == null)
            return new Response(false, "Role not found!", null);
        userRepository.save(new User(userRequestDTO.getUsername(), userRequestDTO.getFirstname(), userRequestDTO.getLastname(), userRequestDTO.getPassword(),
                Collections.singleton(role)));
        return new Response(true, "User saved successfully!", null);
    }

    @Override
    public Response getById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return new Response(false, "User not found!", null);
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname());
        return new Response(userDTO, true);
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
}
