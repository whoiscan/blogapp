package com.example.postservice.service.implementation;

import com.example.postservice.entity.Post;
import com.example.postservice.model.*;
import com.example.postservice.repository.PostRepository;
import com.example.postservice.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Response addNewPost(Post post) {
        postRepository.save(post);
        return new Response(post, true);
    }

    @Override
    public Response deletePost(Long userId, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent() && optionalPost.get().getId() == userId) {
            postRepository.deleteById(postId);
            log.info("Successfully delete post with id: {}", postId);
            return new Response(true, "Successfully deleted", optionalPost);
        } else if (optionalPost.get().getId() != userId) {
            log.error("Could not update post with id: {}", postId);
            return new Response(false, "You cannot update this post", null);
        } else {
            log.error("Could not find post with id: {}", postId);
            return new Response(false, "Could not find post", null);
        }
    }

    @Override
    public Response getAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts == null) {
            return new Response(false, "No posts", null);
        } else {
            return new Response(posts, true);
        }
    }

    @Override
    public Response updatePost(Long userId, Long postId, PostRequestDTO postRequestDTO) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent() && optionalPost.get().getId() == userId) {
            optionalPost.get().setContent(postRequestDTO.getContent());
            optionalPost.get().setTitle(postRequestDTO.getTitle());
            postRepository.save(optionalPost.get());
            return new Response(true, "Successfully updated", optionalPost.get());
        } else if (optionalPost.get().getId() != userId) {
            return new Response(false, "You cannot update this post", null);
        } else {
            return new Response(false, "Cannot find the post", null);
        }
    }

    @Override
    public Response getMyPosts(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        if (posts == null) {
            return new Response(false, "No posts found", null);
        } else {
            return new Response(true, "My posts", posts);
        }
    }

    @Override
    public Response getPost(Long postId) throws JsonProcessingException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent())
            return new Response(false, "Post not found", null);
        Post post = optionalPost.get();
        Response comments = restTemplate.getForObject(
                "http://COMMENT-SERVICE/comments/post/{postId}",
                Response.class,
                post.getId()
        );
        Response user = restTemplate.getForObject(
            "http://USER-SERVICE/users/{userId}",
                Response.class,
                post.getUserId()
        );
        if (!comments.isSuccess() || comments.getData() == null)
            return comments;
        if (!user.isSuccess() || user.getData() == null)
            return user;
        UserDTO userDTO = objectMapper.readValue(objectMapper.writeValueAsString(user.getData()), new TypeReference<UserDTO>() {});
        PostComments postComments = PostComments.builder()
                .id(post.getId())
                .user(userDTO)
                .content(post.getContent())
                .title(post.getTitle())
                .comments((List<CommentDTO>) comments.getData())
                .build();
        return new Response(postComments, true);
    }
}
