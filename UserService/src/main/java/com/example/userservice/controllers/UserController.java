package com.example.userservice.controllers;

import com.example.userservice.models.Response;
import com.example.userservice.models.UserRequestDTO;
import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Response getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Response getOne(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    public Response save(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.save(userRequestDTO);
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.update(id, userRequestDTO);
    }

    @DeleteMapping("/{id}")
    public Response remove(@PathVariable Long id) {
        return userService.delete(id);
    }
}
