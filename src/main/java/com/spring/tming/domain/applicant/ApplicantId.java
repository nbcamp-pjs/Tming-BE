package com.spring.tming.domain.applicant;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.user.entity.User;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantId implements Serializable {

    private Post post;
    private User user;
}
