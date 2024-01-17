package com.spring.tming.domain.chat.dto.request;

import lombok.Getter;

@Getter
public class ChatReq {

    private Long roomId;
    private Long senderId;
    private String content;
}
