package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {

        Comment comment = mapToEntity(commentDTO);

        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id", String.valueOf(postId)));

        //set post to comment entity
        comment.setPost(post);

        //save comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        //retrieve comments by post id
        List<Comment> comments = commentRepository.findByPostId(postId);


        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("","",String.valueOf(postId)));

        //retrieve comment by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment", "id", String.valueOf(commentId)));

        if (comment.getPost().getId()!=(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment doesn't belong to post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", String.valueOf(postId)));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id",String.valueOf(commentId)));

        if(comment.getPost().getId()!= postId){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to post");
        }

        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment) ;
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post","id", String.valueOf(postId)));

        Comment comment= commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id",String.valueOf(commentId)));

        if(comment.getPost().getId()!=post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment doesn't belong to post");
        }

        commentRepository.deleteById(commentId);

    }

    private CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO= mapper.map(comment,CommentDTO.class);
                /*new CommentDTO(comment.getId(),comment.getName(),
                                    comment.getEmail(),comment.getBody());*/
        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO){

        Comment comment = mapper.map(commentDTO,Comment.class); /* new Comment();

        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());*/

       return comment;
    }
}
