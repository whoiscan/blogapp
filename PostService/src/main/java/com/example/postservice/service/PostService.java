package com.example.postservice.service;
import com.example.postservice.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    void addNewPost(Post post);
    void deletePost(Long postId);

    List<Post> getAllPosts();
}
