package com.spring.tming.domain.chat.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomAllResList {
    private List<RoomAllRes> roomAllReses;

    @Builder
    private RoomAllResList(List<RoomAllRes> roomAllReses) {
        this.roomAllReses = roomAllReses;
    }
}
