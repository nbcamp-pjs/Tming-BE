package com.spring.tming.domain.chat.dto.response;

import com.spring.tming.domain.chat.entity.ChatMember;
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
    private List<ChatMember> chatUserList;
    private RoomMessageResList roomMessageResList;

    @Builder
    private RoomGetRes(
            Long chatRoomId,
            String chatRoomName,
            List<ChatMember> chatUserList,
            RoomMessageResList roomMessageResList) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
        this.chatUserList = chatUserList;
        this.roomMessageResList = roomMessageResList;
    }
}
