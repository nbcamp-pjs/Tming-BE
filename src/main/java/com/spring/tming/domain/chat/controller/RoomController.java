package com.spring.tming.domain.chat.controller;

import com.spring.tming.domain.chat.dto.request.RoomGetAllReq;
import com.spring.tming.domain.chat.dto.request.RoomGetReq;
import com.spring.tming.domain.chat.dto.request.RoomSaveReq;
import com.spring.tming.domain.chat.dto.response.RoomGetAllResList;
import com.spring.tming.domain.chat.dto.response.RoomGetRes;
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
    public RestResponse<RoomGetAllResList> getAllRoom(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        RoomGetAllReq roomGetAllReq =
                RoomGetAllReq.builder().userId(userDetails.getUser().getUserId()).build();

        return RestResponse.success(roomService.getAllRoom(roomGetAllReq));
    }

    @GetMapping("/{roomId}")
    public RestResponse<RoomGetRes> getFindRoom(
            @PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        RoomGetReq roomGetReq =
                RoomGetReq.builder().roomId(roomId).userId(userDetails.getUser().getUserId()).build();

        return RestResponse.success(roomService.getFindRoom(roomGetReq));
    }
}
