package com.mavericsystems.postservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mavericsystems.postservice.constant.PostConstant;
import com.mavericsystems.postservice.dto.PostDto;
import com.mavericsystems.postservice.dto.PostRequest;
import com.mavericsystems.postservice.dto.UserDto;
import com.mavericsystems.postservice.exception.PostIdMismatchException;
import com.mavericsystems.postservice.model.Post;
import com.mavericsystems.postservice.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @MockBean
    PostService postService;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllPosts() throws Exception {
        List<PostDto> postDto = createPostsList();
        Mockito.when(postService.getPosts(1, 2)).thenReturn(postDto);
        mockMvc.perform(get("/posts?page=1&pageSize=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].post", Matchers.is("FirstPost")))
                .andExpect(jsonPath("$[1].post", Matchers.is("SecondPost")));
    }

    @Test
    void testUpdatePostById() throws Exception {
        Post post = createOnePostToUpdate();
        PostDto postDto = new PostDto();
        PostRequest postRequest = new PostRequest();
        Mockito.when(postService.updatePost("1", postRequest)).thenReturn(postDto);
        mockMvc.perform(put("/posts/1")
                        .content(asJsonString(post))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testExceptionThrownWhenIdNotFound() throws Exception {
        PostController postController = new PostController();
        PostRequest postRequest = mock(PostRequest.class);
        when(postRequest.getId()).thenReturn("a");
        assertThrows(PostIdMismatchException.class, () -> postController.updatePost("1", postRequest));
        verify(postRequest).getId();
    }

    @Test
    void testGetPostByID() throws Exception {
        PostDto postDto = createOnePost();
        Mockito.when(postService.getPostDetails("1")).thenReturn(postDto);
        mockMvc.perform(get("/posts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(7)))
                .andExpect(jsonPath("$.post", Matchers.is("PostTest")));
    }

    @Test
    void testDeletePostById() throws Exception {
        Mockito.when(postService.deletePost("1")).thenReturn(PostConstant.DELETE_POST);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePost() throws Exception {
        Post post = createOnePostToPost();
        PostDto postDto = new PostDto();
        PostRequest postRequest = new PostRequest();
        Mockito.when(postService.createPost(postRequest)).thenReturn(postDto);
        mockMvc.perform(post("/posts")
                        .content(asJsonString(post))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private List<PostDto> createPostsList() {
        List<PostDto> posts = new ArrayList<>();

        PostDto post1 = new PostDto();
        post1.setId("1");
        post1.setPost("FirstPost");
        post1.setPostedBy(new UserDto("1", "Prabhu", "J", "S", "9090909090", "prabhu@mail.com", null, "123", null, null));
        post1.setCreatedAt(null);
        post1.setUpdatedAt(null);
        post1.setLikesCount(3);
        post1.setCommentsCount(2);

        PostDto post2 = new PostDto();
        post2.setId("2");
        post2.setPost("SecondPost");
        post2.setPostedBy(new UserDto("1", "Prabhu", "J", "S", "9090909090", "prabhu@mail.com", null, "123", null, null));
        post2.setCreatedAt(null);
        post2.setUpdatedAt(null);
        post2.setLikesCount(3);
        post2.setCommentsCount(2);

        posts.add(post1);
        posts.add(post2);
        return posts;
    }

    private Post createOnePostToPost() {
        Post post = new Post();
        post.setId("1");
        post.setPost("Hi");
        post.setPostedBy(String.valueOf(new UserDto("1", "Prabhu", "J", "S", "9090909090", "prabhu@mail.com", null, "123", null, null)));
        return post;
    }

    private PostDto createOnePost() {
        PostDto postDto = new PostDto();
        postDto.setId("2");
        postDto.setPost("PostTest");
        postDto.setPostedBy(new UserDto("1", "Prabhu", "J", "S", "9090909090", "prabhu@mail.com", null, "123", null, null));
        return postDto;
    }

    private Post createOnePostToUpdate() {
        Post post = new Post();
        post.setId("1");
        post.setPost("PostTest");
        post.setPostedBy(String.valueOf(new UserDto("1", "Prabhu", "J", "S", "9090909090", "prabhu@mail.com", null, "123", null, null)));
        return post;
    }

}
