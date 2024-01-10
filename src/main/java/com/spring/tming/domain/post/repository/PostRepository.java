package com.spring.tming.domain.post.repository;

import com.spring.tming.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
