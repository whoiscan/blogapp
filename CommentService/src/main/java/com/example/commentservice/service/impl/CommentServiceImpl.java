package com.example.commentservice.service.impl;

import com.example.commentservice.entity.Comment;
import com.example.commentservice.model.CommentDTO;
import com.example.commentservice.model.CommentRequestDTO;
import com.example.commentservice.model.Response;
import com.example.commentservice.repository.CommentRepository;
import com.example.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Response save(String userId, CommentRequestDTO commentRequestDTO) {
        Date currentDate = new Date();
        Comment comment = commentRepository.save(new Comment(commentRequestDTO.getComment(), currentDate, currentDate,
                Long.valueOf(userId), Long.valueOf(commentRequestDTO.getPostId())));
        return new Response(true, "Comment saved successfully!", parseCommentToDto(comment));
    }

    @Override
    public Response getById(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null)
            return new Response(false, "Comment not found!", null);
        return new Response(parseCommentToDto(comment), true);
    }

    @Override
    public Response delete(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null)
            return new Response(false, "Comment not found!", null);
        commentRepository.delete(comment);
        return new Response(true, "Comment deleted successfully!", null);
    }

    @Override
    public Response update(Long id, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null)
            return new Response(false, "Comment not found!", null);
        comment.setComment(commentRequestDTO.getComment());
        comment.setUpdatedDate(new Date());
        comment.setPostId(Long.valueOf(commentRequestDTO.getPostId()));
        commentRepository.save(comment);
        return new Response(true, "Comment updated successfully!", null);
    }

    @Override
    public Response getAll() {
        return new Response(commentRepository.findAll(), true);
    }

    @Override
    public Response getCommentsByPostId(Long id) {
        return new Response(commentRepository.findCommentsByPostId(id),true);
    }

    private CommentDTO parseCommentToDto(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getComment(),
                comment.getCreatedDate(), comment.getUpdatedDate(),
                comment.getUserId(), comment.getPostId());
    }
}
