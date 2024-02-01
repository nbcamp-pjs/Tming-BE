package com.spring.tming.domain.chat.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomFindReq {

    private Long senderId;
    private Long receiverId;
    private String roomName;

    @Builder
    private RoomFindReq(Long senderId, Long receiverId, String roomName) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.roomName = roomName;
    }
}
