package com.mavericsystems.postservice.service;


import com.mavericsystems.postservice.dto.PostDto;
import com.mavericsystems.postservice.dto.PostRequest;
import com.mavericsystems.postservice.exception.PostNotFoundException;
import com.mavericsystems.postservice.feign.CommentFeign;
import com.mavericsystems.postservice.feign.LikeFeign;
import com.mavericsystems.postservice.feign.UserFeign;
import com.mavericsystems.postservice.model.Post;
import com.mavericsystems.postservice.repo.PostRepo;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import static com.mavericsystems.postservice.constant.PostConstant.*;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepo postRepo;
    @Autowired
    LikeFeign likeFeign;
    @Autowired
    CommentFeign commentFeign;
    @Autowired
    UserFeign userFeign;



    @Override
    public List<Post> getPosts() {
        return postRepo.findAll();
    }



}
