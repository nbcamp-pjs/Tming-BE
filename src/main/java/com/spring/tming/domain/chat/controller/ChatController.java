package com.spring.tming.domain.chat.controller;

import com.spring.tming.domain.chat.dto.request.ChatReq;
import com.spring.tming.domain.chat.dto.response.PublishMessageRes;
import com.spring.tming.domain.chat.service.ChatService;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;
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
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        TimeZone koreaTimeZone = TimeZone.getTimeZone("Asia/Seoul");
        timestamp.setTime(timestamp.getTime() + koreaTimeZone.getRawOffset());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        PublishMessageRes publishMessage =
                PublishMessageRes.builder()
                        .roomId(chatReq.getRoomId())
                        .senderId(chatReq.getSenderId())
                        .content(chatReq.getContent())
                        .createTimestamp(time)
                        .build();

        template.convertAndSend("/sub/v1/rooms/" + publishMessage.getRoomId(), publishMessage);
        chatService.message(roomId, chatReq);
    }
}
