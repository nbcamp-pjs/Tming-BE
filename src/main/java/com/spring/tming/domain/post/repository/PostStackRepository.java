package com.spring.tming.domain.post.repository;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostStackRepository extends JpaRepository<PostStack, Long> {

    @Modifying
    @Query("DELETE FROM PostStack ps WHERE ps.post.postId = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);

    void deleteAllByPost(Post post);
}
