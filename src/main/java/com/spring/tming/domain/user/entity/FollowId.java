package com.spring.tming.domain.user.entity;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowId implements Serializable {
    private Long follower;
    private Long following;
}
