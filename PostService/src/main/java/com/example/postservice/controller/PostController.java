package com.example.postservice.controller;

import com.example.postservice.model.Post;
import com.example.postservice.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public void addNewPost(@RequestBody Post newPost) {
        postService.addNewPost(newPost);
    }

    @DeleteMapping(path = "{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
