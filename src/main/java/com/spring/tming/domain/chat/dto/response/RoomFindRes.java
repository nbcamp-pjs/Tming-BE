package com.spring.tming.domain.chat.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomFindRes {

    private Long chatRoomId;
    private String chatRoomName;

    @Builder
    private RoomFindRes(Long chatRoomId, String chatRoomName) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
    }
}
