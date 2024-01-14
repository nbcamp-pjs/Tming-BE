package com.spring.tming.domain.post.repository;

import com.spring.tming.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByPostId(Long postId);

    List<Post> findAllByOrderByCreateTimestampAsc();

    List<Post> findAllByUserUserIdOrderByCreateTimestampAsc(Long userId);
}
