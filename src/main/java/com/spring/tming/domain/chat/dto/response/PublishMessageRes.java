package com.spring.tming.domain.chat.dto.response;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublishMessageRes implements Serializable {

    @NotNull private Long roomId;
    @NotNull private Long senderId;
    private String content;
    private Timestamp createTimestamp;

    @Builder
    PublishMessageRes(Long roomId, Long senderId, String content, Timestamp createTimestamp) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.createTimestamp = createTimestamp;
    }
}
