package com.spring.tming.domain.chat.service;

import com.spring.tming.domain.chat.dto.request.RoomReq;
import com.spring.tming.domain.chat.dto.response.RoomRes;
import com.spring.tming.domain.chat.entity.ChatMember;
import com.spring.tming.domain.chat.entity.ChatRoom;
import com.spring.tming.domain.chat.repository.ChatMemberRepository;
import com.spring.tming.domain.chat.repository.ChatRoomRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.security.UserDetailsImpl;
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

    public RoomRes createRoom(RoomReq roomReq, UserDetailsImpl userDetails) {
        User receiver = userRepository.findByUserId(roomReq.getUserId());
        UserValidator.validate(receiver);
        List<ChatMember> receiverList = memberRepository.findByUserId(receiver);
        List<ChatMember> senderList = memberRepository.findByUserId(userDetails.getUser());

        // 채팅방 존재하는지 확인
        for (ChatMember member : receiverList) {
            for (ChatMember member2 : senderList) {
                if (member.getChatRoomId() == member2.getChatRoomId()) {
                    return RoomRes.builder().roomId(member.getChatRoomId().getChatRoomId()).build();
                }
            }
        }
        // 채팅방 생성
        ChatRoom createChatRoom =
                roomRepository.save(ChatRoom.builder().chatRoomName(roomReq.getName()).build());
        // 받는 사람 저장
        memberRepository.save(ChatMember.builder().chatRoomId(createChatRoom).userId(receiver).build());
        // 보내는 사람 저장
        memberRepository.save(
                ChatMember.builder().chatRoomId(createChatRoom).userId(userDetails.getUser()).build());

        return RoomRes.builder().roomId(createChatRoom.getChatRoomId()).build();
    }
}
