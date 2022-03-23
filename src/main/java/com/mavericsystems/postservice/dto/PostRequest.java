package com.mavericsystems.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String id;
    @NotEmpty(message = "post should not be empty")
    private String post;
    private String postedBy;
}
