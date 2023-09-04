package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    //create blog post rest API
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){


        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts
    @GetMapping
    public List<PostDto> getAllPosts(){


        return postService.getAllPosts();

        //return new ResponseEntity<>(postService, HttpStatus.CREATED);
    }

    //get post by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getPostById(@PathVariable("id") long id){

        PostDto postDto = postService.getPostById(id);
        return  postDto;
    }

    //update post info
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable("id") long id){

        PostDto postResponse = postService.updatePostById(postDto, id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);

    }

    //delete post by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name="id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("post entity deleted successfully",HttpStatus.OK);

    }

    //get all post using pagination
    @GetMapping("/page")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,required = false )String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false )String sortDir


            ){
      //  List<PostDto> posts= postService.getAllPostsPagination(pageNo, pageSize);


        return postService.getAllPostsPagination(pageNo,pageSize, sortBy, sortDir);
       // return  new ResponseEntity<>(posts,HttpStatus.OK);
    }
}
