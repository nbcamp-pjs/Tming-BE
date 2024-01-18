package com.spring.tming.domain.chat.controller;

import com.spring.tming.domain.chat.dto.request.ChatReq;
import com.spring.tming.domain.chat.dto.response.ChatRes;
import com.spring.tming.domain.chat.dto.response.PublishMessageRes;
import com.spring.tming.domain.chat.service.ChatService;
import com.spring.tming.global.response.RestResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;

    @MessageMapping("/chats/{room-id}")
    public RestResponse<ChatRes> message(
            @DestinationVariable("room-id") Long roomId, ChatReq chatReq) {
        PublishMessageRes publishMessage =
                PublishMessageRes.builder()
                        .roomId(chatReq.getRoomId())
                        .senderId(chatReq.getSenderId())
                        .createTimestamp(Timestamp.valueOf(LocalDateTime.now()))
                        .build();

        template.convertAndSend("sub/v1/rooms" + publishMessage.getRoomId(), publishMessage);

        return RestResponse.success(chatService.message(roomId, chatReq));
    }
}
