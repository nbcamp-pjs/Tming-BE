package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomFindRes {
    private Long chatRoomId;

    @Builder
    private RoomFindRes(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
