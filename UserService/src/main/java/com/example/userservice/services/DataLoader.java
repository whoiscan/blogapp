package com.example.userservice.services;

import com.example.userservice.entities.Role;
import com.example.userservice.entities.User;
import com.example.userservice.repositories.RoleRepository;
import com.example.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Role role = new Role("USER");
        if (roleRepository.findAll().size() == 0) roleRepository.save(role);
        if (userRepository.findAll().size() == 0)
            userRepository.save(new User("johnsmith", "John", "Smith", "$2a$10$OHELRGf8YTBwRYYE8F3v8eCL4fwQZJaK9qTg4VmBXmYnxGIy2b4NG", Collections.singleton(role)));
    }
}