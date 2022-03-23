package com.mavericsystems.postservice.service;


import com.mavericsystems.postservice.dto.PostDto;
import com.mavericsystems.postservice.dto.PostRequest;
import com.mavericsystems.postservice.model.Post;

import java.util.List;

public interface PostService {

    List<Post> getPosts();

}
