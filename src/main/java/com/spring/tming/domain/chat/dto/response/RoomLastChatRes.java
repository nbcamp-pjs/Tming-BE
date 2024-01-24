package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomLastChatRes {

    private String content;
    private String createTimestamp;

    @Builder
    private RoomLastChatRes(String content, String createTimestamp) {
        this.content = content;
        this.createTimestamp = createTimestamp;
    }
}
