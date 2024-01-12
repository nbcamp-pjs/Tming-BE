package com.spring.tming.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private String role;

    private String job;

    private String introduce;
    private String profileImageUrl;

    @Builder
    private User(
            Long userId,
            String email,
            String password,
            String username,
            String role,
            String job,
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
