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
    private String createTimestamp;

    @Builder
    private RoomMessageRes(Long userId, String content, String createTimestamp) {
        this.userId = userId;
        this.content = content;
        this.createTimestamp = createTimestamp;
    }
}
