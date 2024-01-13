package com.spring.tming.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupReq {

    private String email;
    private String password;
    private String username;
    private boolean role;
    private String job;
    private String introduce;
}
