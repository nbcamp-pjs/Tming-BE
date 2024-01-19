package com.spring.tming.domain.chat.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomFindReq {

    private Long roomId;
    private Long senderId;

    @Builder
    private RoomFindReq(Long roomId, Long senderId) {
        this.roomId = roomId;
        this.senderId = senderId;
    }
}
