package com.mavericsystems.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String id;
    private String post;
    private String postedBy;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private int likesCount;
    private int commentsCount;
}
