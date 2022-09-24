package com.example.postservice.service;
import com.example.postservice.entity.Post;
import com.example.postservice.model.PostRequestDTO;
import com.example.postservice.model.Response;

import java.util.List;

public interface PostService {
    Response addNewPost(Post post);
    Response deletePost(Long userId,Long postId);

    Response getAllPosts();

    Response updatePost(Long userId, Long postId, PostRequestDTO postRequestDTO);

    Response getMyPosts(Long userId);
}
