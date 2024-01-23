package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomGetRes {
    private Long chatRoomId;
    private String chatRoomName;
    private Long userId;

    @Builder
    private RoomGetRes(Long chatRoomId, String chatRoomName, Long userId) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
        this.userId = userId;
    }
}
