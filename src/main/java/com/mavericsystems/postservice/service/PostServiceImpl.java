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
    @Override
    public PostDto createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setPost(postRequest.getPost());
        post.setPostedBy(postRequest.getPostedBy());
        post.setCreatedAt(LocalDate.now());
        post.setUpdatedAt(LocalDate.now());
        postRepo.save(post);
        return new PostDto(post.getId(),post.getPost(),post.getPostedBy(),
                post.getCreatedAt(),post.getUpdatedAt(),
                likeFeign.getLikesCount(post.getId())
                ,commentFeign.getCommentsCount(post.getId()));
//                userFeign.getUserById(post.getPostedBy()).getFirstName());

    }

    @Override
    public PostDto getPostDetails(String postId) {

        Optional<Post> post1 = postRepo.findById(postId);
        if(post1.isPresent()) {
            Post post = post1.get();
            return new PostDto(post.getId(), post.getPost(), post.getPostedBy(), post.getCreatedAt(),
                    post.getUpdatedAt(), likeFeign.getLikesCount(postId),
                    commentFeign.getCommentsCount(postId));
//                    userFeign.getUserById(post.getPostedBy()).getFirstName());
        }
        else{
            throw new PostNotFoundException(POSTNOTFOUND + postId);
        }
    }


}
