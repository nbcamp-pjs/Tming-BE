package com.spring.tming.domain.chat.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfoResList {

    private List<RoomInfoRes> roomInfoRese;

    @Builder
    private RoomInfoResList(List<RoomInfoRes> roomInfoRese) {
        this.roomInfoRese = roomInfoRese;
    }
}
