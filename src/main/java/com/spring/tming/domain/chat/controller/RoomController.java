package com.spring.tming.domain.chat.controller;

import com.spring.tming.domain.chat.dto.request.RoomAllReq;
import com.spring.tming.domain.chat.dto.request.RoomSaveReq;
import com.spring.tming.domain.chat.dto.response.RoomAllResList;
import com.spring.tming.domain.chat.dto.response.RoomSaveRes;
import com.spring.tming.domain.chat.service.RoomService;
import com.spring.tming.global.response.RestResponse;
import com.spring.tming.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public RestResponse<RoomSaveRes> createRoom(
            @RequestBody RoomSaveReq roomReq, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        roomReq.setSenderId(userDetails.getUser().getUserId());
        return RestResponse.success(roomService.createRoom(roomReq));
    }

    @GetMapping
    public RestResponse<RoomAllResList> getAllRoom(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        RoomAllReq roomAllReq = RoomAllReq.builder().userId(userDetails.getUser().getUserId()).build();

        return RestResponse.success(roomService.getAllRoom(roomAllReq));
    }
}
