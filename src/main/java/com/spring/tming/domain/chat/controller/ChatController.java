package com.spring.tming.domain.chat.controller;

import com.spring.tming.domain.chat.dto.request.ChatReq;
import com.spring.tming.domain.chat.dto.response.ChatRes;
import com.spring.tming.domain.chat.entity.PublishMessage;
import com.spring.tming.domain.chat.service.ChatService;
import com.spring.tming.global.response.RestResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private SimpMessagingTemplate template;
    private ChatService chatService;

    @MessageMapping("/chats/{room-id}")
    public RestResponse<ChatRes> message(
            @DestinationVariable("room-id") Long roomId, ChatReq chatReq) {
        PublishMessage publishMessage =
                new PublishMessage(
                        chatReq.getRoomId(), chatReq.getSenderId(), chatReq.getContent(), LocalDateTime.now());

        template.convertAndSend("sub/v1/rooms" + publishMessage.getRoomId(), publishMessage);

        return RestResponse.success(chatService.message(roomId, chatReq));
    }
}
