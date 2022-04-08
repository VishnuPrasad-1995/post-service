package com.mavericsystems.postservice.service;

import com.mavericsystems.postservice.constant.PostConstant;
import com.mavericsystems.postservice.dto.PostDto;
import com.mavericsystems.postservice.dto.PostRequest;
import com.mavericsystems.postservice.dto.UserDto;
import com.mavericsystems.postservice.exception.CustomFeignException;
import com.mavericsystems.postservice.exception.PostNotFoundException;
import com.mavericsystems.postservice.feign.CommentFeign;
import com.mavericsystems.postservice.feign.LikeFeign;
import com.mavericsystems.postservice.feign.UserFeign;
import com.mavericsystems.postservice.model.Post;
import com.mavericsystems.postservice.repo.PostRepo;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostServiceTest {

    @InjectMocks
    PostServiceImpl postService;
    @Mock
    PostRepo postRepo;
    @Mock
    UserFeign userFeign;
    @Mock
    CommentFeign commentFeign;
    @Mock
    LikeFeign likeFeign;

    @Test
    void testDeletePostById() {
        postService.deletePost("1");
        verify(postRepo, times(1)).deleteById("1");
    }

    @Test
    void testExceptionThrownWhenIdNotFound() {
        Mockito.doThrow(PostNotFoundException.class).when(postRepo).deleteById(any());
        Exception userNotFoundException = assertThrows(PostNotFoundException.class, () -> postService.deletePost("1"));
        assertTrue(userNotFoundException.getMessage().contains(PostConstant.POST_NOT_FOUND));
    }

    @Test
    void testCreatePost() {
        when(this.userFeign.getUserById((String) any())).thenReturn(new UserDto());

        PostRequest postRequest = new PostRequest("1", "Post", "Posted By");
        Post post = new Post();
        post.setCreatedAt(LocalDate.now());
        post.setId("1");
        post.setPost("Post");
        post.setPostedBy("Posted By");
        post.setUpdatedAt(LocalDate.now());
        when(this.postRepo.save((Post) any())).thenReturn(post);
        when(this.likeFeign.getLikesCount((String) any())).thenReturn(3);
        when(this.commentFeign.getCommentsCount((String) any())).thenThrow(mock(FeignException.class));
        assertThrows(CustomFeignException.class, () -> this.postService.createPost(postRequest));
    }

    @Test
    void testGetPostDetailById() {
        Post post = new Post();
        post.setId("1");
        post.setPost("Hi");

        Mockito.when(postRepo.findById("1")).thenReturn(Optional.ofNullable(post));
        assertThat(postService.getPostDetails(post.getId()));
        assertThrows(PostNotFoundException.class, () -> postService.getPostDetails(null));
    }

    @Test
    void testExceptionThrownWhenFeignConnectionIssueInGetPostDetailByID() {
        when(this.userFeign.getUserById((String) any())).thenReturn(new UserDto());

        Post post = new Post();
        post.setCreatedAt(LocalDate.now());
        post.setId("1");
        post.setPost("Post");
        post.setPostedBy("Posted By");
        post.setUpdatedAt(LocalDate.now());
        Optional<Post> ofResult = Optional.of(post);
        when(this.postRepo.findById((String) any())).thenReturn(ofResult);
        when(this.likeFeign.getLikesCount((String) any())).thenReturn(3);
        when(this.commentFeign.getCommentsCount((String) any())).thenThrow(mock(FeignException.class));
        assertThrows(CustomFeignException.class, () -> this.postService.getPostDetails("1"));
    }

    @Test
    void testUpdatePostById() {
        Post post1 = createOnePost();
        PostDto postDto = createOnePostToResponse1();
        PostRequest postRequest = createOnePostToRequest();
        Mockito.when(postRepo.findById("1")).thenReturn(Optional.ofNullable(post1));
        assertThat(postService.updatePost("1", postRequest)).isEqualTo(postDto);
        assertThrows(PostNotFoundException.class, () -> postService.updatePost(null, postRequest));
    }

    @Test
    void testExceptionThrownWhenFeignConnectionIssueInUpdatePostById() {
        when(this.userFeign.getUserById((String) any())).thenReturn(new UserDto());

        Post post = new Post();
        post.setCreatedAt(LocalDate.now());
        post.setId("1");
        post.setPost("Post");
        post.setPostedBy("Posted By");
        post.setUpdatedAt(LocalDate.now());
        Optional<Post> ofResult = Optional.of(post);

        PostRequest postRequest = new PostRequest("1", "Post", "Posted By");
        Post post1 = new Post();
        post1.setCreatedAt(LocalDate.now());
        post1.setId("1");
        post1.setPost("Post");
        post1.setPostedBy("Posted By");
        post1.setUpdatedAt(LocalDate.now());
        when(this.postRepo.save((Post) any())).thenReturn(post1);
        when(this.postRepo.findById((String) any())).thenReturn(ofResult);
        when(this.likeFeign.getLikesCount((String) any())).thenReturn(3);
        when(this.commentFeign.getCommentsCount((String) any())).thenThrow(mock(FeignException.class));
        assertThrows(CustomFeignException.class, () -> this.postService.updatePost("1", postRequest));
    }

    @Test
    void testGetAllPosts() {
        when(this.userFeign.getUserById((String) any())).thenReturn(new UserDto());

        Post post = new Post();
        post.setCreatedAt(LocalDate.now());
        post.setId("1");
        post.setPost("Post");
        post.setPostedBy("Posted By");
        post.setUpdatedAt(LocalDate.now());

        ArrayList<Post> postList = new ArrayList<>();
        postList.add(post);
        PageImpl<Post> pageImpl = new PageImpl<>(postList);
        when(this.postRepo.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        when(this.likeFeign.getLikesCount((String) any())).thenReturn(3);
        when(this.commentFeign.getCommentsCount((String) any())).thenReturn(3);
        assertEquals(1, this.postService.getPosts(1, 3).size());
    }

    @Test
    void testExceptionThrownWhenFeignConnectionFailed() {
        when(this.userFeign.getUserById((String) any())).thenReturn(new UserDto());

        Post post = new Post();
        post.setCreatedAt(LocalDate.now());
        post.setId("1");
        post.setPost("Post");
        post.setPostedBy("Posted By");
        post.setUpdatedAt(LocalDate.now());

        ArrayList<Post> postList = new ArrayList<>();
        postList.add(post);
        PageImpl<Post> pageImpl = new PageImpl<>(postList);
        when(this.postRepo.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        when(this.likeFeign.getLikesCount((String) any())).thenReturn(3);
        when(this.commentFeign.getCommentsCount((String) any())).thenThrow(mock(FeignException.class));
        assertThrows(CustomFeignException.class, () -> this.postService.getPosts(1, 3));
    }

    private PostRequest createOnePostToRequest() {
        PostRequest postRequest = new PostRequest();
        postRequest.setPost("Hi");
        postRequest.setPostedBy("2");
        return postRequest;
    }

    private Post createOnePost() {
        Post post1 = new Post();
        post1.setId("1");
        post1.setPost("Hi");
        post1.setCreatedAt(LocalDate.now());
        post1.setCreatedAt(LocalDate.now());
        return post1;
    }

    private PostDto createOnePostToResponse1() {
        PostDto postDto = new PostDto();
        postDto.setId("1");
        postDto.setPost("Hi");
        postDto.setPostedBy(userFeign.getUserById("1"));
        postDto.setCreatedAt(LocalDate.now());
        postDto.setUpdatedAt(LocalDate.now());
        postDto.setCommentsCount(commentFeign.getCommentsCount("1"));
        postDto.setLikesCount(likeFeign.getLikesCount("1"));
        return postDto;
    }
}
