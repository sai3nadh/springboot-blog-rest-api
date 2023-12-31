package com.springboot.blog.controller;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@Valid @PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDTO commentDTO){

        return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getAllComments(@PathVariable(value = "postId") long postId){

        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "postId")  long postId,
                                                     @PathVariable(name = "id") long commentId){

        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(commentDTO,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("postId")  long postId,
                                                    @PathVariable("commentId") long commentId,
                                                    @Valid @RequestBody CommentDTO commentDTO){

        CommentDTO updatedComment = commentService.updateComment(postId,commentId,commentDTO);

        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long postId,
                                                @PathVariable("id") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("comment Deleted Successfully",HttpStatus.OK);
    }
}
