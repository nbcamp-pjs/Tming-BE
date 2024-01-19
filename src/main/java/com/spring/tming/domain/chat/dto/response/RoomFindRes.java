package com.spring.tming.domain.chat.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomFindRes {

    private Long roomId;
    private String roomName;

    @Builder
    private RoomFindRes(Long roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }
}
