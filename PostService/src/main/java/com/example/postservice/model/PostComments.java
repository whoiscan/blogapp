package com.example.postservice.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
public class PostComments {
    private Long id;
    private String title;
    private String content;
    private UserDTO user;
    private List<CommentDTO> comments = new ArrayList<>();
}
