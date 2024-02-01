package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfoRes {
    private Long chatRoomId;
    private String chatRoomName;
    private Long userId;
    private String username;

    @Builder
    private RoomInfoRes(Long chatRoomId, String chatRoomName, Long userId, String username) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
        this.userId = userId;
        this.username = username;
    }
}
