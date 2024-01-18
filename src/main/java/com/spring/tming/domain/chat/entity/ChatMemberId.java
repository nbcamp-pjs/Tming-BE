package com.spring.tming.domain.chat.entity;

import com.spring.tming.domain.user.entity.User;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMemberId implements Serializable {
    private User userId;
    private ChatRoom chatRoomId;
}
