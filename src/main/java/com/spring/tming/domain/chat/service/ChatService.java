package com.spring.tming.domain.chat.service;

import com.spring.tming.domain.chat.dto.request.ChatReq;
import com.spring.tming.domain.chat.dto.response.ChatRes;
import com.spring.tming.domain.chat.entity.Chat;
import com.spring.tming.domain.chat.entity.ChatRoom;
import com.spring.tming.domain.chat.repository.ChatRepository;
import com.spring.tming.domain.chat.repository.ChatRoomRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.validator.ChatRoomValidator;
import com.spring.tming.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository roomRepository;

    public ChatRes message(Long roomId, ChatReq chatReq) {
        User user = userRepository.findByUserId(chatReq.getSenderId());
        UserValidator.validate(user);
        ChatRoom chatRoom = roomRepository.findByChatRoomId(roomId);
        ChatRoomValidator.validate(chatRoom);

        chatRepository.save(
                Chat.builder().chatRoom(chatRoom).userId(user).content(chatReq.getContent()).build());

        return new ChatRes();
    }
}
