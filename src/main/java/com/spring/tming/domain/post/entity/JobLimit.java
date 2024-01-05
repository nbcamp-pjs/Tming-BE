package com.spring.tming.domain.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_jobLimit")
public class JobLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobLimitId;

    @Enumerated(EnumType.STRING)
    private Job job;

    @Column private Integer headcount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;
}
