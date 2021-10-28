package br.com.carlosjunior.blog.service.impl;

import br.com.carlosjunior.blog.entity.Post;
import br.com.carlosjunior.blog.exception.ResourceNotFoundException;
import br.com.carlosjunior.blog.payload.PostDto;
import br.com.carlosjunior.blog.repository.PostRepository;
import br.com.carlosjunior.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //Converter DTO to entity
        Post post = mapToPost(postDto);
        //Save new Post.
        Post newPost = postRepository.save(post);
        //Convert entity to DTO
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        //get to list and convert
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        //Get post by Id from the database
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        //Update data
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        //Save Update
        Post updatePost = postRepository.save(post);
        return mapToDto(updatePost);
    }

    @Override
    public void deletePostById(Long id) {
        //First, get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    //Convert Entity to PostDTO
        private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    //Convert PostDTO to Entity
    private Post mapToPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

}
