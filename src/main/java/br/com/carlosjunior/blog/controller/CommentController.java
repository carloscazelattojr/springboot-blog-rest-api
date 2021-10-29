package br.com.carlosjunior.blog.controller;


import br.com.carlosjunior.blog.payload.CommentDto;
import br.com.carlosjunior.blog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    public ResponseEntity<CommentDto> createComment(Long id, CommentDto commentDto){
        
    }

}
