package com.example.commentservice.controller;

import com.example.commentservice.model.CommentRequestDTO;
import com.example.commentservice.model.Response;
import com.example.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public Response getAll() {
        return commentService.getAll();
    }

    @GetMapping("/{id}")
    public Response getOne(@PathVariable Long id) {
        return commentService.getById(id);
    }

    @GetMapping("/post/{id}")
    public Response getByPostId(@PathVariable Long id) {
        return commentService.getCommentsByPostId(id);
    }

    @PostMapping
    public Response save(@RequestHeader(value = "id") String userId, @RequestBody CommentRequestDTO commentRequestDTO) {
        return commentService.save(userId, commentRequestDTO);
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody CommentRequestDTO commentRequestDTO) {
        return commentService.update(id, commentRequestDTO);
    }

    @DeleteMapping("/{id}")
    public Response remove(@PathVariable Long id) {
        return commentService.delete(id);
    }
}
