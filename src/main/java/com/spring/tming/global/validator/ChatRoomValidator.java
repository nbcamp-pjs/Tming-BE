package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.CHATROOM_MISMATCH;
import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_CHATROOM;

import com.spring.tming.domain.chat.entity.ChatMember;
import com.spring.tming.domain.chat.entity.ChatRoom;
import com.spring.tming.global.exception.GlobalException;
import java.util.List;

public class ChatRoomValidator {

    public static void validate(ChatRoom chatRoom) {
        if (isNullChatRoom(chatRoom)) {
            throw new GlobalException(NOT_FOUND_CHATROOM);
        }
    }

    public static void checkRoomList(List<ChatMember> chatMember) {
        if (isNulllChatMemberList(chatMember)) {
            throw new GlobalException(NOT_FOUND_CHATROOM);
        }
    }

    public static void checkUserChatRoom(List<ChatMember> roomList, Long roomId) {
        ChatMember member =
                roomList.stream()
                        .filter(chatMember -> chatMember.getChatRoomId().getChatRoomId().equals(roomId))
                        .findFirst()
                        .orElse(null);
        if (isNullChatMember(member)) {
            throw new GlobalException(CHATROOM_MISMATCH);
        }
    }

    private static boolean isNullChatMember(ChatMember chatMember) {
        return chatMember == null;
    }

    private static boolean isNulllChatMemberList(List<ChatMember> chatMemberList) {
        return chatMemberList.isEmpty();
    }

    private static boolean isNullChatRoom(ChatRoom chatRoom) {
        return chatRoom == null;
    }
}
