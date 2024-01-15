package com.spring.tming.domain.post.entity;

import com.spring.tming.global.meta.Skill;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_postStack")
public class PostStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postStackId;

    @Enumerated(EnumType.STRING)
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @Builder
    private PostStack(Long postStackId, Skill skill, Post post) {
        this.postStackId = postStackId;
        this.skill = skill;
        this.post = post;
    }
}
