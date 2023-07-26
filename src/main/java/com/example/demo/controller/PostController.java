package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.model.properties.PostProperties;
import com.example.demo.model.request.CreatePostRequest;
import com.example.demo.service.PostService;
import org.springframework.web.bind.annotation.*;


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
        Post post = postService.getPostById(id).orElse(null);
        return post;
    }

    // mi adunk meg egy JSON-t és az lesz az új poszt (postmanban)
    @PostMapping(value = "/post")
    public Post createNewPost(@RequestBody CreatePostRequest request) {
        return postService.createNewPost(request);
    }

    @GetMapping(value = "/post/settings")
    public PostProperties getPostSettings() {
        return postProperties;
    }



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
