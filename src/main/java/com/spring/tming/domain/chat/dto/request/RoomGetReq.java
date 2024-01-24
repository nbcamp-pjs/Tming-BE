package com.spring.tming.domain.chat.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomGetReq {
    private Long userId;
    private Long roomId;

    @Builder
    private RoomGetReq(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
