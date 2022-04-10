package com.mavericsystems.postservice.service;


import com.mavericsystems.postservice.dto.PostDto;
import com.mavericsystems.postservice.dto.PostRequest;

import java.util.List;

public interface PostService {

    List<PostDto> getPosts(Integer page, Integer pageSize);
    PostDto createPost(PostRequest postRequest);
    PostDto getPostDetails(String postId);
    PostDto updatePost(String postId, PostRequest postRequest);
    String deletePost(String postId);
}
