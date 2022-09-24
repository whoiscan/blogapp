package com.example.postservice.controller;

import com.example.postservice.entity.Post;
import com.example.postservice.model.PostRequestDTO;
import com.example.postservice.model.Response;
import com.example.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public Response getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public Response addNewPost(@Valid @RequestBody PostRequestDTO postRequestDTO,
                               @RequestHeader(value = "id") Long userId) {
        Post tempPost = new Post(userId,postRequestDTO.getTitle(),postRequestDTO.getContent());
        return postService.addNewPost(tempPost);
    }

    @DeleteMapping(path = "{postId}")
    public Response deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @PutMapping(path = "{postId}")
    public Response updatePost(@PathVariable Long postId,
                               @RequestHeader(value = "id") Long userId,
                               @Valid @RequestBody PostRequestDTO postRequestDTO) {
        return postService.updatePost(userId,postId, postRequestDTO);
    }
}
