package com.spring.tming.domain.members.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmitMemberReq {
    private Long postId;
    private Long userId;

    @Builder
    private EmitMemberReq(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}
