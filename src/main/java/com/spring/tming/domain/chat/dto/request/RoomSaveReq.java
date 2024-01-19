package com.spring.tming.domain.chat.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomSaveReq {

    private Long userId;
    private String name;
    private Long senderId;

    @Builder
    private RoomSaveReq(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
