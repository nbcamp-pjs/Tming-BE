package com.spring.tming.domain.chat.service;

import com.spring.tming.domain.chat.dto.request.RoomReadReq;
import com.spring.tming.domain.chat.dto.request.RoomSaveReq;
import com.spring.tming.domain.chat.dto.response.RoomReadRes;
import com.spring.tming.domain.chat.dto.response.RoomReadResList;
import com.spring.tming.domain.chat.dto.response.RoomSaveRes;
import com.spring.tming.domain.chat.entity.ChatMember;
import com.spring.tming.domain.chat.entity.ChatRoom;
import com.spring.tming.domain.chat.repository.ChatMemberRepository;
import com.spring.tming.domain.chat.repository.ChatRepository;
import com.spring.tming.domain.chat.repository.ChatRoomRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.validator.ChatRoomValidator;
import com.spring.tming.global.validator.UserValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final ChatMemberRepository memberRepository;
    private final ChatRoomRepository roomRepository;
    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    public RoomSaveRes createRoom(RoomSaveReq roomReq) {
        User receiver = userRepository.findByUserId(roomReq.getUserId());
        UserValidator.validate(receiver);

        User sender = userRepository.findByUserId(roomReq.getSenderId());

        // 채팅방 생성
        ChatRoom createChatRoom =
                roomRepository.save(ChatRoom.builder().chatRoomName(roomReq.getName()).build());
        // 받는 사람 저장
        memberRepository.save(ChatMember.builder().chatRoomId(createChatRoom).userId(receiver).build());
        // 보내는 사람 저장
        memberRepository.save(ChatMember.builder().chatRoomId(createChatRoom).userId(sender).build());

        return ChatRoomServiceMapper.INSTANCE.toRoomSaveRes(createChatRoom);
    }

    public RoomReadResList getReadRoom(RoomReadReq roomReadReq) {
        ChatRoom chatRoom = roomRepository.findByChatRoomId(roomReadReq.getRoomId());
        ChatRoomValidator.validate(chatRoom);

        List<RoomReadRes> roomReadResList =
                ChatRoomServiceMapper.INSTANCE.toRoomReadResList(
                        chatRepository.findByChatRoomIdChatRoomId(roomReadReq.getRoomId()));

        return RoomReadResList.builder().roomReadRes(roomReadResList).build();
    }
}
