package br.com.carlosjunior.blog.service;

import br.com.carlosjunior.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id );

    void deletePostById(Long id);
}
