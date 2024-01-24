package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_CHATROOM;

import com.spring.tming.domain.chat.entity.ChatMember;
import com.spring.tming.domain.chat.entity.ChatRoom;
import com.spring.tming.global.exception.GlobalException;

public class ChatRoomValidator {

    public static void validate(ChatRoom chatRoom) {
        if (isNullChatRoom(chatRoom)) {
            throw new GlobalException(NOT_FOUND_CHATROOM);
        }
    }

    public static void checkRoomList(ChatMember chatMember) {
        if (isNullChatMember(chatMember)) {
            throw new GlobalException(NOT_FOUND_CHATROOM);
        }
    }

    private static boolean isNullChatMember(ChatMember chatMember) {
        return chatMember == null;
    }

    private static boolean isNullChatRoom(ChatRoom chatRoom) {
        return chatRoom == null;
    }
}
