package br.com.carlosjunior.blog.service;

import br.com.carlosjunior.blog.payload.PostDto;
import br.com.carlosjunior.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id );

    void deletePostById(Long id);
}
