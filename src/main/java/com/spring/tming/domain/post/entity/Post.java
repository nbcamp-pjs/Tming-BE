package com.spring.tming.domain.post.entity;

import com.spring.tming.domain.applicant.entity.Applicant;
import com.spring.tming.domain.comment.entity.Comment;
import com.spring.tming.domain.members.entity.Member;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Column(length = 30)
    private String title;

    @Column(length = 1500)
    private String content;

    @Column private Timestamp deadline;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column private Long visit;

    @Column private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "post", targetEntity = PostStack.class, cascade = CascadeType.ALL)
    private final Set<PostStack> postStacks = new HashSet<>();

    @OneToMany(mappedBy = "post", targetEntity = JobLimit.class, cascade = CascadeType.ALL)
    private final List<JobLimit> jobLimits = new ArrayList<>();

    @OneToMany(mappedBy = "post", targetEntity = Comment.class, cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", targetEntity = PostLike.class, cascade = CascadeType.ALL)
    private final List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post", targetEntity = Applicant.class, cascade = CascadeType.ALL)
    private final List<Applicant> applicants = new ArrayList<>();

    @OneToMany(mappedBy = "post", targetEntity = Member.class, cascade = CascadeType.ALL)
    private final List<Member> members = new ArrayList<>();

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
