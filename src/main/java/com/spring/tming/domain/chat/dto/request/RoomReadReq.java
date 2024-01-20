package com.spring.tming.domain.chat.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomReadReq {

    private Long userId;
    private Long roomId;

    @Builder
    private RoomReadReq(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
