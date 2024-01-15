package com.spring.tming.domain.user.dto.request;

import com.spring.tming.global.meta.Job;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupReq {

    private String email;
    private String password;
    private String username;
    private Job job;

    @Builder
    private SignupReq(String email, String password, String username, Job job) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.job = job;
    }
}
