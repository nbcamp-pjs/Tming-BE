package com.spring.tming.domain.members.entity;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.meta.Job;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_members")
@IdClass(MemberId.class)
public class Member {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    private Post post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Job job;

    @Builder
    private Member(Post post, User user, Job job) {
        this.post = post;
        this.user = user;
        this.job = job;
    }
}
