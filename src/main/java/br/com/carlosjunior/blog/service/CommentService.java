package br.com.carlosjunior.blog.service;

import br.com.carlosjunior.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId,CommentDto commentDto);

    List<CommentDto> getCommentByPostId(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId,Long commentId, CommentDto commentDto);

    void deleteComment(Long postId, Long commentId);
}
