package com.example.commentservice.service;

import com.example.commentservice.model.CommentRequestDTO;
import com.example.commentservice.model.Response;

public interface CommentService {
    Response save(CommentRequestDTO commentRequestDTO);

    Response getById(Long id);

    Response delete(Long id);

    Response update(Long id, CommentRequestDTO commentRequestDTO);

    Response getAll();
}