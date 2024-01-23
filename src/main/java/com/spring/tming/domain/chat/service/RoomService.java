package com.spring.tming.domain.chat.service;

import com.spring.tming.domain.chat.dto.request.RoomAllReq;
import com.spring.tming.domain.chat.dto.request.RoomGetReq;
import com.spring.tming.domain.chat.dto.request.RoomReadReq;
import com.spring.tming.domain.chat.dto.request.RoomSaveReq;
import com.spring.tming.domain.chat.dto.response.*;
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

    public RoomAllResList getAllRoom(RoomAllReq roomAllReq) {
        RoomGetReq roomGetReq =
                RoomGetReq.builder().userId(userRepository.findByUserId(roomAllReq.getUserId())).build();
        RoomGetResList roomGetResList = getRoomList(roomGetReq);
        List<RoomAllRes> roomAllResList = new ArrayList<>();
        for (RoomGetRes roomGetRes : roomGetResList.getRoomGetReses()) {
            RoomReadReq roomReadReq =
                    RoomReadReq.builder()
                            .roomId(roomGetRes.getChatRoomId())
                            .userId(roomAllReq.getUserId())
                            .build();
            RoomReadResList roomReadResList = getReadRoom(roomReadReq);

            RoomAllRes roomAllRes =
                    RoomAllRes.builder()
                            .userId(roomAllReq.getUserId())
                            .roomGetRes(roomGetRes)
                            .roomReadResList(roomReadResList)
                            .build();
            roomAllResList.add(roomAllRes);
        }

        return RoomAllResList.builder().roomAllReses(roomAllResList).build();
    }

    public RoomGetResList getRoomList(RoomGetReq roomGetReq) { // req->user
        // 유저아이디 ->채팅방 아이디
        List<ChatMember> chatMemberList = memberRepository.findByUserId(roomGetReq.getUserId());
        ChatRoomValidator.checkRoomList(chatMemberList.get(0));
        List<RoomGetRes> roomGetResList = new ArrayList<>();

        for (ChatMember chatMember : chatMemberList) {
            // 상대방 아이디
            ChatRoom chatRoom =
                    roomRepository.findByChatRoomId(chatMember.getChatRoomId().getChatRoomId());
            ChatMember receiver =
                    memberRepository.findByChatRoomIdAndUserIdNot(chatRoom, roomGetReq.getUserId());
            System.out.println("user RECEIVER ID NOT" + receiver.getUserId().getUserId());
            RoomGetRes roomGetRes =
                    RoomGetRes.builder()
                            .chatRoomId(chatRoom.getChatRoomId())
                            .chatRoomName(chatRoom.getChatRoomName())
                            .userId(receiver.getUserId().getUserId())
                            .build();

            roomGetResList.add(roomGetRes);
        }

        return RoomGetResList.builder().roomGetReses(roomGetResList).build();
    }

    public RoomReadResList getReadRoom(RoomReadReq roomReadReq) {
        // 유저아이디 채팅방아이디->채팅 전체조회
        ChatRoom chatRoom = roomRepository.findByChatRoomId(roomReadReq.getRoomId());
        ChatRoomValidator.validate(chatRoom);

        List<RoomReadRes> roomReadResList =
                ChatRoomServiceMapper.INSTANCE.toRoomReadResList(
                        chatRepository.findByChatRoomIdChatRoomId(roomReadReq.getRoomId()));

        return RoomReadResList.builder().roomReadRes(roomReadResList).build();
    }

    //    public RoomGetResList getRoomList(RoomGetReq roomGetReq) {
    //        List<ChatMember> chatMemberList=memberRepository.findByUserId(roomGetReq.getUserId());
    //
    //        List<RoomGetRes> roomGetResList=ChatRoomServiceMapper.INSTANCE.toRoomGetResList();
    //
    //    }
}
