package com.spring.tming.domain.chat.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomGetResList {

    private List<RoomGetRes> roomGetReses;

    @Builder
    private RoomGetResList(List<RoomGetRes> roomGetReses) {
        this.roomGetReses = roomGetReses;
    }
}
