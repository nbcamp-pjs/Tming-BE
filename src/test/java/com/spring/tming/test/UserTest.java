package com.spring.tming.test;

import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.entity.Role;
import com.spring.tming.global.meta.Job;

public interface UserTest {

    Long TEST_USER_ID = 1L;

    String TEST_USER_NAME = "username";
    String TEST_USER_PASSWORD = "ABcd5678#&";
    String TEST_USER_EMAIL = "username@gmail.com";
    String TEST_USER_INTRODUCE = "introduce";
    String TEST_USER_PROFILE_URL = "resources/images/sparta.png";

    // TODO fix enum
    Role TEST_USER_ROLE = Role.USER;
    Job TEST_USER_JOB = Job.BACKEND;

    User TEST_USER =
            User.builder()
                    .userId(TEST_USER_ID)
                    .email(TEST_USER_EMAIL)
                    .username(TEST_USER_NAME)
                    .password(TEST_USER_PASSWORD)
                    .role(TEST_USER_ROLE)
                    .job(TEST_USER_JOB)
                    .introduce(TEST_USER_INTRODUCE)
                    .profileImageUrl(TEST_USER_PROFILE_URL)
                    .build();
}
