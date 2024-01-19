package com.spring.tming.domain.chat.service;

import com.spring.tming.domain.chat.dto.response.RoomSaveRes;
import com.spring.tming.domain.chat.entity.ChatRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatServiceMapper {
    ChatServiceMapper INSTANCE = Mappers.getMapper(ChatServiceMapper.class);

    @Mapping(source = "chatRoomId", target = "roomId")
    RoomSaveRes toRoomSaveRes(ChatRoom chatRoom);
}
