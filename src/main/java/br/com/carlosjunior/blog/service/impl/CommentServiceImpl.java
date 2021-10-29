package br.com.carlosjunior.blog.service.impl;

import br.com.carlosjunior.blog.entity.Comment;
import br.com.carlosjunior.blog.entity.Post;
import br.com.carlosjunior.blog.exception.ResourceNotFoundException;
import br.com.carlosjunior.blog.payload.CommentDto;
import br.com.carlosjunior.blog.payload.PostDto;
import br.com.carlosjunior.blog.repository.CommentRepository;
import br.com.carlosjunior.blog.repository.PostRepository;
import br.com.carlosjunior.blog.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        // retrive post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id", postId));

        // set post to comment entity
        comment.setPost(post);

        //save new comment
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);


    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        return comment;
    }





}
