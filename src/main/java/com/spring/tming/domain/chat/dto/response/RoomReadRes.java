package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomReadRes {

    private Long userId;
    private String content;

    @Builder
    private RoomReadRes(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
