package com.spring.tming.domain.chat.dto.response;

import java.io.Serializable;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublishMessageRes implements Serializable {

    @NotNull private Long roomId;
    @NotNull private Long senderId;
    private String content;
    private String createTimestamp;

    @Builder
    private PublishMessageRes(Long roomId, Long senderId, String content, String createTimestamp) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.createTimestamp = createTimestamp;
    }
}
