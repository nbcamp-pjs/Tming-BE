package com.spring.tming.domain.chat.controller;

import com.spring.tming.domain.chat.dto.request.ChatReq;
import com.spring.tming.domain.chat.dto.response.ChatRes;
import com.spring.tming.domain.chat.dto.response.PublishMessageRes;
import com.spring.tming.domain.chat.service.ChatService;
import com.spring.tming.global.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;

    @MessageMapping("/chats/{room-id}")
    public RestResponse<ChatRes> message(
            @DestinationVariable("room-id") Long roomId, ChatReq chatReq) {
        PublishMessageRes publishMessage =
                new PublishMessageRes(
                        chatReq.getRoomId(), chatReq.getSenderId(), chatReq.getContent(), LocalDateTime.now());

        template.convertAndSend("sub/v1/rooms" + publishMessage.getRoomId(), publishMessage);

        return RestResponse.success(chatService.message(roomId, chatReq));
    }
}
