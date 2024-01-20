package com.spring.tming.domain.chat.service;

import com.spring.tming.domain.chat.dto.response.RoomReadRes;
import com.spring.tming.domain.chat.dto.response.RoomSaveRes;
import com.spring.tming.domain.chat.entity.Chat;
import com.spring.tming.domain.chat.entity.ChatRoom;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatRoomServiceMapper {
    ChatRoomServiceMapper INSTANCE = Mappers.getMapper(ChatRoomServiceMapper.class);

    @Mapping(source = "chatRoomId", target = "roomId")
    RoomSaveRes toRoomSaveRes(ChatRoom chatRoom);

    @Mapping(source = "chat.userId.userId", target = "userId")
    @Mapping(source = "content", target = "content")
    RoomReadRes toRoomReadRes(Chat chat);

    @Mapping(source = "chat.userId.userId", target = "userId")
    @Mapping(source = "content", target = "content")
    List<RoomReadRes> toRoomReadResList(List<Chat> chat);
}
