package com.spring.tming.domain.post.entity;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeId implements Serializable {
    private Long user;
    private Long post;
}
