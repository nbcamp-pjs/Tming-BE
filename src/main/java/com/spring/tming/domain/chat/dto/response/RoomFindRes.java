package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomFindRes {
    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;

    @Builder
    private RoomFindRes(Long chatRoomId, Long senderId, Long receiverId) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
