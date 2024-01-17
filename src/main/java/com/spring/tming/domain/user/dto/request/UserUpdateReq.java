package com.spring.tming.domain.user.dto.request;

import com.spring.tming.global.meta.Job;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateReq {
    private Long userId;
    private String password;
    private String username;
    private Job job;
    private String introduce;

    @Builder
    private UserUpdateReq(Long userId, String password, String username, Job job, String introduce) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.job = job;
        this.introduce = introduce;
    }
}
