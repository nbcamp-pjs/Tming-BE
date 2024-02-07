package com.spring.tming.domain.chat.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.BaseMvcTest;
import com.spring.tming.domain.chat.dto.request.RoomFindReq;
import com.spring.tming.domain.chat.dto.request.RoomGetAllReq;
import com.spring.tming.domain.chat.dto.request.RoomGetReq;
import com.spring.tming.domain.chat.dto.request.RoomSaveReq;
import com.spring.tming.domain.chat.dto.response.*;
import com.spring.tming.domain.chat.service.RoomService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = {RoomController.class})
class RoomControllerTest extends BaseMvcTest {
    @MockBean private RoomService roomService;

    @Test
    @DisplayName("채팅방 생성")
    void createRoom() throws Exception {
        Long userId = 1L;
        String name = "방이름";
        Long senderId = 2l;
        Long roomId = 1L;

        RoomSaveReq roomSaveReq =
                RoomSaveReq.builder().userId(userId).name(name).senderId(senderId).build();
        RoomSaveRes roomSaveRes = RoomSaveRes.builder().roomId(roomId).build();

        when(roomService.createRoom(any())).thenReturn(roomSaveRes);

        this.mockMvc
                .perform(
                        post("/v1/rooms")
                                .principal(this.mockPrincipal)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(roomSaveReq)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("채팅방 찾기")
    void findRoom() throws Exception {
        Long senderId = 1L;
        Long receiverId = 3L;
        String roomName = "없으면생기는방이름";
        Long chatRoomId = 2L;

        RoomFindReq roomFindReq = RoomFindReq.builder().senderId(senderId).build();
        RoomFindRes roomFindRes = RoomFindRes.builder().chatRoomId(chatRoomId).build();
        when(roomService.findRoom(any())).thenReturn(roomFindRes);

        ResultActions resultActions =
                mockMvc.perform(
                        get("/v1/rooms/chat")
                                .principal(this.mockPrincipal)
                                .param("receiverId", String.valueOf(receiverId))
                                .param("roomName", roomName)
                                .content(objectMapper.writeValueAsString(roomFindReq))
                                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    @DisplayName("채팅방 전체 조회")
    void getAllRoom() throws Exception {
        Long userId = 1L;
        String content = "마지막채팅";
        String createTimestamp = String.valueOf(Timestamp.valueOf(LocalDateTime.now()));
        RoomLastChatRes roomLastChatRes =
                RoomLastChatRes.builder().content(content).createTimestamp(createTimestamp).build();

        Long chatRoomId = 1L;
        String chatRoomName = "방이름";
        Long receiverId = 2L;
        String username = "user1";
        String imageUrl = "url";
        RoomInfoRes roomInfoRes =
                RoomInfoRes.builder()
                        .chatRoomId(chatRoomId)
                        .chatRoomName(chatRoomName)
                        .userId(receiverId)
                        .username(username)
                        .imageUrl(imageUrl)
                        .build();

        RoomGetAllRes roomGetAllRes =
                RoomGetAllRes.builder()
                        .userId(userId)
                        .roomLastChatRes(roomLastChatRes)
                        .roomInfoRes(roomInfoRes)
                        .build();

        RoomGetAllResList roomGetAllResList =
                RoomGetAllResList.builder().roomGetAllReses(List.of(roomGetAllRes)).build();
        RoomGetAllReq roomGetAllReq = RoomGetAllReq.builder().userId(userId).build();

        when(roomService.getAllRoom(any())).thenReturn(roomGetAllResList);

        this.mockMvc
                .perform(
                        get("/v1/rooms")
                                .principal(this.mockPrincipal)
                                .content(objectMapper.writeValueAsString(roomGetAllReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("채팅방 단건 조회")
    void getFindRoom() throws Exception {
        Long chatRoomId = 1L;
        String chatRoomName = "방이름";

        Long chatUserId = 1L;
        String username = "user1";
        String imageUrl = "url";
        List<RoomUserInfoRes> roomUserInfoResList =
                List.of(
                        RoomUserInfoRes.builder()
                                .userId(chatUserId)
                                .username(username)
                                .imageUrl(imageUrl)
                                .build());

        Long userId = 2L;
        String content = "채팅내용";
        String createTimestamp = String.valueOf(Timestamp.valueOf(LocalDateTime.now()));
        RoomMessageRes roomMessageRes =
                RoomMessageRes.builder()
                        .content(content)
                        .userId(userId)
                        .createTimestamp(createTimestamp)
                        .build();
        RoomMessageResList roomMessageResList =
                RoomMessageResList.builder().roomMessageRes(List.of(roomMessageRes)).build();

        RoomGetReq roomGetReq = RoomGetReq.builder().userId(chatUserId).roomId(chatRoomId).build();
        RoomGetRes roomGetRes =
                RoomGetRes.builder()
                        .chatRoomId(chatRoomId)
                        .chatRoomName(chatRoomName)
                        .roomUserInfoReses(roomUserInfoResList)
                        .roomMessageResList(roomMessageResList)
                        .build();
        when(roomService.getFindRoom(any())).thenReturn(roomGetRes);

        this.mockMvc
                .perform(
                        get("/v1/rooms/" + chatRoomId)
                                .principal(this.mockPrincipal)
                                .content(objectMapper.writeValueAsString(roomGetReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
