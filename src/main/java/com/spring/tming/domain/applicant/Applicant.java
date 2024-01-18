package com.spring.tming.domain.applicant;

import com.spring.tming.global.meta.Job;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_applicant")
@IdClass(Applicant.class)
public class Applicant implements Serializable {

    @Id
    @Column(name = "post_id")
    private Long postId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Job job;

    @Builder
    private Applicant(Long postId, Long userId, Job job) {
        this.postId = postId;
        this.userId = userId;
        this.job = job;
    }
}
