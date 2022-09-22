package com.example.userservice.services;

import com.example.userservice.models.Response;
import com.example.userservice.models.UserRequestDTO;

public interface UserService {
    Response save(UserRequestDTO userRequestDTO);

    Response getById(Long id);

    Response delete(Long id);

    Response update(Long id, UserRequestDTO userRequestDTO);

    Response getAll();
}
