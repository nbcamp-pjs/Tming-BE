package com.spring.tming.domain.members.entity;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembersId implements Serializable {

    private Long post;
    private Long user;

    @Builder
    private MembersId(Long post, Long user) {
        this.post = post;
        this.user = user;
    }
}
