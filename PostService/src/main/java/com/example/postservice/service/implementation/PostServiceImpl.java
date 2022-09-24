package com.example.postservice.service.implementation;

import com.example.postservice.entity.Post;
import com.example.postservice.model.PostRequestDTO;
import com.example.postservice.model.Response;
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
    public Response addNewPost(Post post) {
        postRepository.save(post);
        return new Response(post,true);
    }

    @Override
    public Response deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(postId);
            log.info("Successfully delete post with id: {}", postId);
            return new Response(true,"Successfully deleted",optionalPost);
        } else {
            log.error("Could not find post with id: {}", postId);
            return new Response(false,"Could not find post",null);
        }
    }

    @Override
    public Response getAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts == null) {
            return new Response(false, "No posts",null);
        } else {
            return new Response(posts,true);
        }
    }

    @Override
    public Response updatePost(Long userId, Long postId, PostRequestDTO postRequestDTO) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent() && optionalPost.get().getId() == userId) {
            optionalPost.get().setContent(postRequestDTO.getContent());
            optionalPost.get().setTitle(postRequestDTO.getTitle());
            postRepository.save(optionalPost.get());
            return new Response(true,"Successfully updated",optionalPost.get());
        } else if (optionalPost.get().getId() != userId) {
            return new Response(false,"You cannot update this post",null);
        } else {
            return new Response(false,"Cannot find the post",null);
        }
    }
}
