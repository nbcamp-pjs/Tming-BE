package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomRes {

    private Long roomId;

    @Builder
    private RoomRes(Long roomId) {
        this.roomId = roomId;
    }
}
