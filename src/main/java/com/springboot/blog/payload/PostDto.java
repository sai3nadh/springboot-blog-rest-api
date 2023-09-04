package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {

    private long id;

    //title !=null or empty
    //title should have 2 chars
    @NotEmpty
    @Size(min = 2, message = "post should have min of 2 chars")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "post desc min 10 chars")
    private String description;

    @NotEmpty
    private String content;

    private List<CommentDTO> comments;

}
