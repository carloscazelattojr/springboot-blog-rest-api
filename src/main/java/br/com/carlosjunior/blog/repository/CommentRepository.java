package br.com.carlosjunior.blog.repository;

import br.com.carlosjunior.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
