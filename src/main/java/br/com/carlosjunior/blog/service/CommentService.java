package br.com.carlosjunior.blog.service;

import br.com.carlosjunior.blog.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(Long postId,CommentDto commentDto);

}
