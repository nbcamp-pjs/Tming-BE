package com.spring.tming.domain.chat.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomSaveReq {

    private Long userId;
    private String name;
    private Long senderId;

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    @Builder
    private RoomSaveReq(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
