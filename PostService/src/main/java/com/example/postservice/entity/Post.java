package com.example.postservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private Long user_id;
    @Column(unique = true)
    private String title;
    private String content;

    public Post(Long user_id, String title, String content) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
    }
}
