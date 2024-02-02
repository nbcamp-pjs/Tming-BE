package com.spring.tming.domain.chat.config;

import com.spring.tming.global.jwt.JwtUtil;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            if (!jwtUtil.validateAccessToken(
                    Objects.requireNonNull(accessor.getFirstNativeHeader("AccessToken")).substring(7))) {
                jwtUtil.validateRefreshToken(
                        Objects.requireNonNull(accessor.getFirstNativeHeader("RefreshToken")).substring(7));
            }
        }
        return message;
    }
}
