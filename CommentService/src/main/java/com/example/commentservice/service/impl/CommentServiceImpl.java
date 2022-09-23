package com.example.commentservice.service.impl;

import com.example.commentservice.model.CommentRequestDTO;
import com.example.commentservice.model.Response;
import com.example.commentservice.repository.CommentRepository;
import com.example.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Response save(CommentRequestDTO commentRequestDTO) {
        return null;
    }

    @Override
    public Response getById(Long id) {
        return null;
    }

    @Override
    public Response delete(Long id) {
        return null;
    }

    @Override
    public Response update(Long id, CommentRequestDTO commentRequestDTO) {
        return null;
    }

    @Override
    public Response getAll() {
        return null;
    }
}
