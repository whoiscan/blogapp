package com.example.commentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDTO {
    private String comment;
    private Date createdDate;
    private Date updatedDate;
}