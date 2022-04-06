package com.mavericsystems.postservice.controller;

import com.mavericsystems.postservice.dto.PostDto;
import com.mavericsystems.postservice.dto.PostRequest;
import com.mavericsystems.postservice.exception.PostIdMismatchException;
import com.mavericsystems.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;
import static com.mavericsystems.postservice.constant.PostConstant.POSTIDMISMATCH;

@CrossOrigin (origins = "http://localhost:8080")
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize)
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
    public ResponseEntity<PostDto> updatePost(@PathVariable("postId") String postId,@Valid @RequestBody PostRequest postRequest)
    {
        if(postRequest.getId().equals(postId)){
            return new ResponseEntity<>(postService.updatePost(postId, postRequest), HttpStatus.OK);
        }
        else {
            throw new PostIdMismatchException(POSTIDMISMATCH);
        }
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") String postId)
    {
        return new ResponseEntity<>(postService.deletePost(postId), HttpStatus.OK);
    }

}
