package com.spring.tming.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomAllRes {

    private Long userId;
    private RoomGetRes roomGetRes;
    private RoomReadResList roomReadResList;

    @Builder
    private RoomAllRes(Long userId, RoomGetRes roomGetRes, RoomReadResList roomReadResList) {
        this.userId = userId;
        this.roomGetRes = roomGetRes;
        this.roomReadResList = roomReadResList;
    }
}
