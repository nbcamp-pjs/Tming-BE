package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomMessageRes {

    private Long userId;
    private String content;

    @Builder
    private RoomMessageRes(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
