package com.spring.tming.domain.applicant.entity;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.meta.Job;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_applicant")
@IdClass(ApplicantId.class)
public class Applicant {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Job job;

    @Builder
    private Applicant(Post post, User user, Job job) {
        this.post = post;
        this.user = user;
        this.job = job;
    }
}
