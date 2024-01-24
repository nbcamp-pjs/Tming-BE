package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomGetAllRes {

    private Long userId;
    private RoomInfoRes roomInfoRes;
    private RoomLastChatRes roomLastChatRes;

    @Builder
    private RoomGetAllRes(Long userId, RoomInfoRes roomInfoRes, RoomLastChatRes roomLastChatRes) {
        this.userId = userId;
        this.roomInfoRes = roomInfoRes;
        this.roomLastChatRes = roomLastChatRes;
    }
}
