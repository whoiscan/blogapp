package com.example.postservice.implementation;

import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import com.example.postservice.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public void addNewPost(Post post) {
        try {
            postRepository.save(post);
        } catch (Exception e) {
            log.error("Failed to create new post");
        }
    }

    @Override
    public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(postId);
            log.info("Successfully delete post with id: {}", postId);
        } else {
            log.error("Could not find post with id: {}", postId);
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
