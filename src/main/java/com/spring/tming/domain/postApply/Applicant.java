package com.spring.tming.domain.postApply;

import com.spring.tming.domain.post.entity.Job;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_applicant")
public class Applicant {

    @EmbeddedId private ApplicantId id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Job job;

    @Builder
    private Applicant(ApplicantId id, Job job) {
        this.id = id;
        this.job = job;
    }
}
