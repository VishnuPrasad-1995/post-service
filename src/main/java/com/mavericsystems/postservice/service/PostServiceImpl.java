package com.mavericsystems.postservice.service;


import com.mavericsystems.postservice.dto.PostDto;
import com.mavericsystems.postservice.dto.PostRequest;
import com.mavericsystems.postservice.exception.PostNotFoundException;
import com.mavericsystems.postservice.feign.CommentFeign;
import com.mavericsystems.postservice.feign.LikeFeign;
import com.mavericsystems.postservice.feign.UserFeign;
import com.mavericsystems.postservice.model.Post;
import com.mavericsystems.postservice.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<Post> getPosts(Integer page, Integer pageSize) {
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        Page<Post> posts = postRepo.findAll(PageRequest.of(page-1, pageSize));

        return posts.toList();
    }
    @Override
    public PostDto createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setPost(postRequest.getPost());
        post.setPostedBy(postRequest.getPostedBy());
        post.setCreatedAt(LocalDate.now());
        post.setUpdatedAt(LocalDate.now());
        postRepo.save(post);
        String email =  userFeign.getUserById(post.getPostedBy()).getEmail();
        return new PostDto(post.getId(),post.getPost(),email,
                post.getCreatedAt(),post.getUpdatedAt(),
                likeFeign.getLikesCount(post.getId())
                ,commentFeign.getCommentsCount(post.getId()));


    }

    @Override
    public PostDto getPostDetails(String postId) {

        Optional<Post> post1 = postRepo.findById(postId);
        if(post1.isPresent()) {
            Post post = post1.get();

            return new PostDto(post.getId(), post.getPost(), userFeign.getUserById(post.getPostedBy()).getEmail(), post.getCreatedAt(),
                    post.getUpdatedAt(), likeFeign.getLikesCount(postId),
                    commentFeign.getCommentsCount(postId));

        }
        else{
            throw new PostNotFoundException(POSTNOTFOUND + postId);
        }
    }

    @Override
    public Post updatePost(String postId, PostRequest postRequest) {



        Optional<Post> post = postRepo.findById(postId);
        if(post.isPresent()) {
            Post post1 = post.get();
            post1.setPost(postRequest.getPost());
            post1.setUpdatedAt(LocalDate.now());
            return postRepo.save(post1);
        }
        else {
            throw new PostNotFoundException(POSTNOTFOUND + postId);
        }
    }



    @Override
    public String deletePost(String postId) {
        try {
            postRepo.deleteById(postId);
            return DELETEPOST;
        }
        catch (Exception e){
            throw new PostNotFoundException(POSTNOTFOUND + postId);
        }
    }


}
