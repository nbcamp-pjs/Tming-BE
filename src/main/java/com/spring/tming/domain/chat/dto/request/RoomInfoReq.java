package com.spring.tming.domain.chat.dto.request;

import com.spring.tming.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfoReq {
    private User userId;

    @Builder
    private RoomInfoReq(User userId) {
        this.userId = userId;
    }
}
