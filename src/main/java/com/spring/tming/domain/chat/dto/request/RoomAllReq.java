package com.spring.tming.domain.chat.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomAllReq {
    private Long userId;

    @Builder
    private RoomAllReq(Long userId) {
        this.userId = userId;
    }
}
