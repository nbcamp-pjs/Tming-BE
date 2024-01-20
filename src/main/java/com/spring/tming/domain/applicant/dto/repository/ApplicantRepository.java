package com.spring.tming.domain.applicant.dto.repository;

import com.spring.tming.domain.applicant.entity.Applicant;
import com.spring.tming.domain.applicant.entity.ApplicantId;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, ApplicantId> {

    Applicant findByPostAndUser(Post post, User user);
    // Applicant findByPostAndUserAndJob(Post post, User user, Job job);

    List<Applicant> findByPostPostId(Long postId);
}
