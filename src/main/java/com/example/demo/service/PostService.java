package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.model.request.CreatePostRequest;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(long id) {
        return postRepository.findById(id);
    }

    public Post createNewPost(CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setCreatedOn(ZonedDateTime.now());
        post.setAuthor(userService.getUserById(request.getAuthorId()).orElseThrow());
        post.setSlug(request.getSlug());
        post.setTopic(request.getTopic());
        return postRepository.save(post);
    }
}
