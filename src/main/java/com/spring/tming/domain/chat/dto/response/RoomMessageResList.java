package com.spring.tming.domain.chat.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomMessageResList {

    private List<RoomMessageRes> roomMessageRese;

    @Builder
    private RoomMessageResList(List<RoomMessageRes> roomMessageRes) {
        this.roomMessageRese = roomMessageRes;
    }
}
