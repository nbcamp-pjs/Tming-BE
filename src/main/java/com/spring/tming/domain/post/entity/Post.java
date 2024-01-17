package com.spring.tming.domain.post.entity;

import com.spring.tming.domain.model.BaseEntity;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.meta.Status;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column private String title;

    @Column private String content;

    @Column private Timestamp deadline;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column private Long visit;

    @Column private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(
            mappedBy = "post",
            targetEntity = PostStack.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<PostStack> postStacks = new ArrayList<>();

    @OneToMany(
            mappedBy = "post",
            targetEntity = JobLimit.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<JobLimit> jobLimits = new ArrayList<>();

    @Builder(toBuilder = true)
    private Post(
            Long postId,
            String title,
            String content,
            Timestamp deadline,
            Status status,
            Long visit,
            String imageUrl,
            User user) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.status = status;
        this.visit = visit;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
