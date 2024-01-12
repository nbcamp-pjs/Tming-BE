package com.spring.tming.domain.post.repository;

import com.spring.tming.domain.post.entity.PostStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostStackRepository extends JpaRepository<PostStack, Long> {

    void deleteByPostPostId(Long postId);
}
