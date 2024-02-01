package com.spring.tming.domain.chat.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomGetRes {

    private Long chatRoomId;
    private String chatRoomName;
    private List<Long> chatUserList;
    private RoomMessageResList roomMessageResList;

    @Builder
    private RoomGetRes(
            Long chatRoomId,
            String chatRoomName,
            List<Long> chatUserList,
            RoomMessageResList roomMessageResList) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
        this.chatUserList = chatUserList;
        this.roomMessageResList = roomMessageResList;
    }
}
