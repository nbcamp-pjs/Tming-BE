package com.spring.tming.domain.chat.controller;

import com.spring.tming.domain.chat.dto.request.RoomReq;
import com.spring.tming.domain.chat.dto.response.RoomRes;
import com.spring.tming.domain.chat.repository.ChatMemberRepository;
import com.spring.tming.domain.chat.repository.ChatRoomRepository;
import com.spring.tming.domain.chat.service.RoomService;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.response.RestResponse;
import com.spring.tming.global.security.UserDetailsImpl;
import com.spring.tming.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rooms")
public class RoomController {

    private final UserRepository userRepository;
    private final ChatMemberRepository memberRepository;
    private final ChatRoomRepository roomRepository;

    private final RoomService roomService;

    @PostMapping
    public RestResponse<RoomRes> createRoom(
            @RequestBody RoomReq roomReq, UserDetailsImpl userDetails) {

        // 보내는사람
        UserValidator.validate(userDetails.getUser());

        return RestResponse.success(roomService.createRoom(roomReq, userDetails));
    }
}
