package com.spring.tming.domain.post.repository;

import com.spring.tming.domain.post.entity.JobLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobLimitRepository extends JpaRepository<JobLimit, Long> {

    @Modifying
    @Query("DELETE FROM JobLimit jl WHERE jl.post.postId = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);
}
