package com.spring.tming.domain.chat.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfoRes {
    private Long chatRoomId;
    private String chatRoomName;
    private Long userId;
    private String username;
    private String imageUrl;

    @Builder
    private RoomInfoRes(
            Long chatRoomId, String chatRoomName, Long userId, String username, String imageUrl) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
        this.userId = userId;
        this.username = username;
        this.imageUrl = imageUrl;
    }
}
