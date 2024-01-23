package com.spring.tming.domain.members.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class emitMemberReq {
    private Long userId;

    @Builder
    private emitMemberReq(Long userId) {
        this.userId = userId;
    }
}
