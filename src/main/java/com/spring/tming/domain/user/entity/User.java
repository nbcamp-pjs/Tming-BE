package com.spring.tming.domain.user.entity;

import com.spring.tming.domain.post.entity.Job;
import com.spring.tming.global.entity.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;
    private String password;
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Job job;

    private String introduce;
    private String profileImageUrl;

    @Builder
    private User(
            Long userId,
            String email,
            String password,
            String username,
            Role role,
            Job job,
            String introduce,
            String profileImageUrl) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.job = job;
        this.introduce = introduce;
        this.profileImageUrl = profileImageUrl;
    }
}
