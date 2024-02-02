package com.spring.tming.domain.chat.service;

import com.spring.tming.domain.chat.dto.request.RoomInfoReq;
import com.spring.tming.domain.chat.dto.request.RoomMessageReq;
import com.spring.tming.domain.chat.dto.response.RoomInfoRes;
import com.spring.tming.domain.chat.dto.response.RoomLastChatRes;
import com.spring.tming.domain.chat.dto.response.RoomMessageRes;
import com.spring.tming.domain.chat.dto.response.RoomSaveRes;
import com.spring.tming.domain.chat.dto.response.RoomUserInfoRes;
import com.spring.tming.domain.chat.entity.Chat;
import com.spring.tming.domain.chat.entity.ChatMember;
import com.spring.tming.domain.chat.entity.ChatRoom;
import com.spring.tming.domain.user.entity.User;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatRoomServiceMapper {
    ChatRoomServiceMapper INSTANCE = Mappers.getMapper(ChatRoomServiceMapper.class);

    @Mapping(source = "createTimestamp", target = "createTimestamp")
    default String toCreateTimeStamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        LocalDateTime localDateTime =
                timestamp.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Mapping(source = "chatRoomId", target = "roomId")
    RoomSaveRes toRoomSaveRes(ChatRoom chatRoom);

    @Mapping(source = "chat.userId.userId", target = "userId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "createTimestamp", target = "createTimestamp")
    RoomMessageRes toRoomMessageRes(Chat chat);

    @Mapping(source = "chat.userId.userId", target = "userId")
    @Mapping(source = "content", target = "content")
    List<RoomMessageRes> toRoomMessageResList(List<Chat> chat);

    @Mapping(source = "createTimestamp", target = "createTimestamp")
    RoomLastChatRes toRoomLastChatRes(Chat chat);

    @Mapping(source = "chatRoom.chatRoomId", target = "chatRoomId")
    @Mapping(source = "chatMember.userId.userId", target = "userId")
    @Mapping(source = "receiver.username", target = "username")
    @Mapping(source = "sender.profileImageUrl", target = "imageUrl")
    RoomInfoRes toRoomInfoRes(ChatRoom chatRoom, User receiver, User sender, ChatMember chatMember);

    @Mapping(source = "user", target = "userId")
    RoomInfoReq toRoomInfoReq(User user);

    @Mapping(source = "chatMember.userId.userId", target = "userId")
    @Mapping(source = "chatMember.chatRoomId.chatRoomId", target = "roomId")
    RoomMessageReq toRoomMessageReq(ChatMember chatMember);

    @Mapping(source = "userId.userId", target = "userId")
    @Mapping(source = "userId.username", target = "username")
    @Mapping(source = "userId.profileImageUrl", target = "imageUrl")
    RoomUserInfoRes toRoomUserInfoRes(ChatMember chatMember);

    List<RoomUserInfoRes> toRoomUserInfoReses(List<ChatMember> chatMembers);
}
