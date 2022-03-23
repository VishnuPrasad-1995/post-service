package com.mavericsystems.postservice.controller;

import com.mavericsystems.postservice.dto.PostDto;
import com.mavericsystems.postservice.dto.PostRequest;
import com.mavericsystems.postservice.model.Post;
import com.mavericsystems.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize)
    {
        return new ResponseEntity<>(postService.getPosts(page, pageSize), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostRequest postRequest)
    {
        return new ResponseEntity<>(postService.createPost(postRequest), HttpStatus.CREATED);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostDetails(@PathVariable("postId") String postId)
    {
        return new ResponseEntity<>(postService.getPostDetails(postId), HttpStatus.OK);
    }
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable("postId") String postId,@Valid @RequestBody PostRequest postRequest)
    {
        return new ResponseEntity<>(postService.updatePost(postId, postRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") String postId)
    {
        return new ResponseEntity<>(postService.deletePost(postId), HttpStatus.OK);
    }



}
