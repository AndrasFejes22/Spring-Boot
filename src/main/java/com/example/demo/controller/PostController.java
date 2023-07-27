package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.exception.NotFoundException;
import com.example.demo.model.properties.PostProperties;
import com.example.demo.model.request.CreatePostRequest;
import com.example.demo.model.response.ApiError;
import com.example.demo.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


//@Controller
@RestController
public class PostController {

    private final PostService postService;
    private final PostProperties postProperties;

    public PostController(PostService postService, PostProperties postProperties) {
        this.postService = postService;
        this.postProperties = postProperties;
    }

    //@GetMapping(value = "/post")
    //@ResponseBody
    @GetMapping(value = "/post/{id}")
    public Post getPostById(@PathVariable long id) {
        //Post post = postService.getPostById(id).orElse(null);
        // exception:
        //Post post = postService.getPostById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Post post = postService.getPostById(id)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));
        return post;
    }

    // mi adunk meg egy JSON-t és az lesz az új poszt (postmanban)
    @PostMapping(value = "/post")
    public Post createNewPost(@RequestBody CreatePostRequest request) {
        return postService.createNewPost(request);
    }


    //settings.posts.publishing.delay=30m
    // 30 m = 30 minutes, felismeri, lehet Java Time (Duration)
    @GetMapping(value = "/post/settings")
    public PostProperties getPostSettings() {
        //postProperties.getPublishing().getStatus(); // ..ignored, ennek ellenére kiírja a statust és a delay-t IS...:(
        postProperties.getPublishing().getDelay(); // ..ignored, ennek ellenére kiírja a statust és a delay-t IS...:(
        return postProperties;
    }


    /*
    //kvázi kivételkezelő metódus (jsont ad vissza) /átrakva a GlobalExceptionHandler-be/
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage()));
    }
    */



    /*
    //postmanban kitöltött és új posztként létrehozva:
    @PostMapping(value = "/post")
    public Post createNewPost(CreatePostRequest request) {
        return postService.createNewPost(request);
    }

    */

    /*
    @GetMapping(value = "/post", produces = "text/plain;charset=utf-8")
    public ResponseEntity<Post> getPostById(@RequestParam long id) {
        Post post = postService.getPostById(id).orElse(null);
        return ResponseEntity.ok().header("Hello", "World").body(post);
    }

    */
}
