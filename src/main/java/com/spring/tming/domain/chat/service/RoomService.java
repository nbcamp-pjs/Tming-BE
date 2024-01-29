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
import java.util.List;
import java.util.stream.Collectors;
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
        // 채팅내용 불러오기
        RoomMessageReq roomMessageReq =
                ChatRoomServiceMapper.INSTANCE.toRoomMessageReq(
                        memberRepository.findByUserIdUserId(roomGetReq.getUserId()));
        RoomMessageResList roomMessageResList = getMessageRoom(roomMessageReq);

        // 채팅방 불러오기
        ChatRoom chatRoom = roomRepository.findByChatRoomId(roomGetReq.getRoomId());

        // 채팅 다수 상대 조회
        List<ChatMember> chatUserList = memberRepository.findByChatRoomId(chatRoom);

        return RoomGetRes.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .chatRoomName(chatRoom.getChatRoomName())
                .chatUserList(chatUserList)
                .roomMessageResList(roomMessageResList)
                .build();
    }

    // 채팅방 전체 조회
    public RoomGetAllResList getAllRoom(RoomGetAllReq roomGetAllReq) {

        RoomInfoReq roomInfoReq =
                ChatRoomServiceMapper.INSTANCE.toRoomInfoReq(
                        userRepository.findByUserId(roomGetAllReq.getUserId()));

        // 채팅방 전체 불러오기
        RoomInfoResList roomInfoResList = getRoomList(roomInfoReq);
        List<RoomGetAllRes> roomGetAllResList =
                roomInfoResList.getRoomInfoRese().stream()
                        .map(
                                roomInfoRes -> {
                                    // 전체 채팅
                                    RoomMessageReq roomMessageReq =
                                            ChatRoomServiceMapper.INSTANCE.toRoomMessageReq(
                                                    memberRepository.findByUserIdUserId(roomInfoRes.getUserId()));
                                    RoomMessageResList roomMessageResList = getMessageRoom(roomMessageReq);
                                    // 마지막 채팅
                                    RoomLastChatRes roomLastChatRes =
                                            ChatRoomServiceMapper.INSTANCE.toRoomLastChatRes(
                                                    lastChat(roomInfoRes.getChatRoomId()));

                                    return RoomGetAllRes.builder()
                                            .userId(roomGetAllReq.getUserId())
                                            .roomInfoRes(roomInfoRes)
                                            .roomLastChatRes(roomLastChatRes)
                                            .build();
                                })
                        .collect(Collectors.toList());

        return RoomGetAllResList.builder().roomGetAllReses(roomGetAllResList).build();
    }

    public RoomInfoResList getRoomList(RoomInfoReq roomInfoReq) { // req->user
        // 유저아이디 ->채팅방 아이디
        List<ChatMember> chatMemberList = memberRepository.findByUserId(roomInfoReq.getUserId());
        ChatRoomValidator.checkRoomList(chatMemberList.get(0));
        // 채팅방 정보(roomId, roomName, receiverId)
        List<RoomInfoRes> roomInfoResList =
                chatMemberList.stream()
                        .map(
                                chatMember -> {
                                    ChatRoom chatRoom =
                                            roomRepository.findByChatRoomId(chatMember.getChatRoomId().getChatRoomId());
                                    return ChatRoomServiceMapper.INSTANCE.toRoomInfoRes(
                                            chatRoom,
                                            memberRepository.findByChatRoomIdAndUserIdNot(
                                                    chatRoom, roomInfoReq.getUserId()));
                                })
                        .collect(Collectors.toList());
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
