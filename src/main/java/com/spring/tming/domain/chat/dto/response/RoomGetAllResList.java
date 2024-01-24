package com.spring.tming.domain.chat.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomGetAllResList {
    private List<RoomGetAllRes> roomGetAllReses;

    @Builder
    private RoomGetAllResList(List<RoomGetAllRes> roomGetAllReses) {
        this.roomGetAllReses = roomGetAllReses;
    }
}
