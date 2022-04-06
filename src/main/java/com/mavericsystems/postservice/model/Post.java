package com.mavericsystems.postservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "postTable")
@Data
@EntityScan
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    private String id;
    private String post;
    private String postedBy;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
