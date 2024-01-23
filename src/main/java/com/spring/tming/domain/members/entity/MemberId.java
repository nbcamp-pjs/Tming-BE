package com.spring.tming.domain.members.entity;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberId implements Serializable {

    private Long post;
    private Long user;

    @Builder
    private MemberId(Long post, Long user) {
        this.post = post;
        this.user = user;
    }
}
