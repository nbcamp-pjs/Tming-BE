package com.spring.tming.domain.chat.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomReadResList {

    private List<RoomReadRes> roomReadRese;

    @Builder
    private RoomReadResList(List<RoomReadRes> roomReadRes) {
        this.roomReadRese = roomReadRes;
    }
}
