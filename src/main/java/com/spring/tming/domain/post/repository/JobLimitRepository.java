package com.spring.tming.domain.post.repository;

import com.spring.tming.domain.post.entity.JobLimit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobLimitRepository extends JpaRepository<JobLimit, Long> {

    void deleteByPostPostId(Long postId);

    List<JobLimit> findAllByPostPostId(Long postId);
}
