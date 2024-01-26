package com.spring.tming.domain.chat.service;

import com.spring.tming.domain.chat.dto.request.*;
import com.spring.tming.domain.chat.dto.response.*;
import com.spring.tming.domain.chat.entity.Chat;
import com.spring.tming.domain.chat.entity.ChatMember;
import com.spring.tming.domain.chat.entity.ChatRoom;
import com.spring.tming.domain.chat.repository.ChatMemberRepository;
import com.spring.tming.domain.chat.repository.ChatRepository;
import com.spring.tming.domain.chat.repository.ChatRoomRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.validator.ChatRoomValidator;
import com.spring.tming.global.validator.UserValidator;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
    // 채팅방 단건조회
    public RoomGetRes getFindRoom(RoomGetReq roomGetReq) {
        RoomMessageReq roomMessageReq =
                RoomMessageReq.builder()
                        .roomId(roomGetReq.getRoomId())
                        .userId(roomGetReq.getUserId())
                        .build();
        RoomMessageResList roomMessageResList = getMessageRoom(roomMessageReq);

        ChatRoom chatRoom = roomRepository.findByChatRoomId(roomGetReq.getRoomId());
        ChatMember receiver =
                memberRepository.findByChatRoomIdAndUserIdNot(
                        chatRoom, userRepository.findByUserId(roomGetReq.getUserId()));

        return RoomGetRes.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .chatRoomName(chatRoom.getChatRoomName())
                .userId(receiver.getUserId().getUserId())
                .roomMessageResList(roomMessageResList)
                .build();
    }

    // 채팅방 전체 조회
    public RoomGetAllResList getAllRoom(RoomGetAllReq roomGetAllReq) {
        RoomInfoReq roomInfoReq =
                RoomInfoReq.builder()
                        .userId(userRepository.findByUserId(roomGetAllReq.getUserId()))
                        .build();
        RoomInfoResList roomInfoResList = getRoomList(roomInfoReq);
        List<RoomGetAllRes> roomGetAllResList = new ArrayList<>();
        for (RoomInfoRes roomInfoRes : roomInfoResList.getRoomInfoRese()) {
            RoomMessageReq roomMessageReq =
                    RoomMessageReq.builder()
                            .roomId(roomInfoRes.getChatRoomId())
                            .userId(roomGetAllReq.getUserId())
                            .build();
            RoomMessageResList roomMessageResList = getMessageRoom(roomMessageReq);

            RoomLastChatRes roomLastChatRes =
                    ChatRoomServiceMapper.INSTANCE.toRoomLastChatRes(lastChat(roomInfoRes.getChatRoomId()));
            RoomGetAllRes roomGetAllRes =
                    RoomGetAllRes.builder()
                            .userId(roomGetAllReq.getUserId())
                            .roomInfoRes(roomInfoRes)
                            .roomLastChatRes(roomLastChatRes)
                            .build();
            roomGetAllResList.add(roomGetAllRes);
        }

        return RoomGetAllResList.builder().roomGetAllReses(roomGetAllResList).build();
    }

    // 채팅방 정보
    public RoomInfoResList getRoomList(RoomInfoReq roomInfoReq) { // req->user
        // 유저아이디 ->채팅방 아이디
        List<ChatMember> chatMemberList = memberRepository.findByUserId(roomInfoReq.getUserId());
        ChatRoomValidator.checkRoomList(chatMemberList.get(0));
        List<RoomInfoRes> roomInfoResList = new ArrayList<>();

        for (ChatMember chatMember : chatMemberList) {
            // 상대방 아이디
            ChatRoom chatRoom =
                    roomRepository.findByChatRoomId(chatMember.getChatRoomId().getChatRoomId());
            ChatMember receiver =
                    memberRepository.findByChatRoomIdAndUserIdNot(chatRoom, roomInfoReq.getUserId());

            RoomInfoRes roomInfoRes =
                    RoomInfoRes.builder()
                            .chatRoomId(chatRoom.getChatRoomId())
                            .chatRoomName(chatRoom.getChatRoomName())
                            .userId(receiver.getUserId().getUserId())
                            .build();

            roomInfoResList.add(roomInfoRes);
        }

        return RoomInfoResList.builder().roomInfoRese(roomInfoResList).build();
    }

    // 채팅 메세지 전체 조회
    public RoomMessageResList getMessageRoom(RoomMessageReq roomMessageReq) {
        // 유저아이디,채팅방아이디->채팅 전체조회
        ChatRoom chatRoom = roomRepository.findByChatRoomId(roomMessageReq.getRoomId());
        ChatRoomValidator.validate(chatRoom);

        List<RoomMessageRes> roomMessageResList =
                ChatRoomServiceMapper.INSTANCE.toRoomMessageResList(
                        chatRepository.findByChatRoomIdChatRoomIdOrderByCreateTimestamp(
                                roomMessageReq.getRoomId()));

        return RoomMessageResList.builder().roomMessageRes(roomMessageResList).build();
    }

    // 마지막 메세지 조회
    public Chat lastChat(Long roomId) {
        Chat lastChat = chatRepository.findTop1ByChatRoomIdChatRoomIdOrderByCreateTimestampDesc(roomId);

        return lastChat;
    }
}
