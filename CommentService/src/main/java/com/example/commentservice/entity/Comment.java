package com.example.commentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private Date createdDate;

    private Date updatedDate;

    private Long userId;

    private Long postId;

    public Comment(String comment, Date createdDate, Date updatedDate, Long userId, Long postId) {
        this.comment = comment;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.userId = userId;
        this.postId = postId;
    }
}
