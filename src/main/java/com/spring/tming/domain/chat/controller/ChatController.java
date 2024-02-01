package com.spring.tming.domain.chat.controller;

import com.spring.tming.domain.chat.dto.request.ChatReq;
import com.spring.tming.domain.chat.dto.response.PublishMessageRes;
import com.spring.tming.domain.chat.service.ChatService;
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

    @MessageMapping("/chats/{roomId}")
    public void message(@DestinationVariable("roomId") Long roomId, ChatReq chatReq) {
        PublishMessageRes publishMessage =
                PublishMessageRes.builder()
                        .roomId(chatReq.getRoomId())
                        .senderId(chatReq.getSenderId())
                        .content(chatReq.getContent())
                        .createTimestamp(Timestamp.valueOf(LocalDateTime.now()))
                        .build();

        template.convertAndSend("/sub/v1/rooms/" + publishMessage.getRoomId(), publishMessage);
        chatService.message(roomId, chatReq);
    }
}
