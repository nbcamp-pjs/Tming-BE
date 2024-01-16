package com.spring.tming.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.BaseMvcTest;
import com.spring.tming.domain.user.dto.request.FollowReq;
import com.spring.tming.domain.user.dto.response.FollowRes;
import com.spring.tming.domain.user.dto.response.UserGetRes;
import com.spring.tming.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest extends BaseMvcTest {
    @MockBean private UserService userService;

    @Test
    @DisplayName("유저 프로필 조회 테스트")
    void 유저_프로필_조회() throws Exception {
        Long userId = 1L;
        String email = "email@domain.com";
        String username = "ysys";
        String job = "Backend Developer";
        String introduce = "intro";
        int following = 1;
        int follower = 1;
        String profileImageUrl = "url";
        UserGetRes userGetRes =
                UserGetRes.builder()
                        .userId(userId)
                        .email(email)
                        .username(username)
                        .job(job)
                        .introduce(introduce)
                        .following(following)
                        .follower(follower)
                        .profileImageUrl(profileImageUrl)
                        .build();
        when(userService.getUserProfile(any())).thenReturn(userGetRes);
        this.mockMvc.perform(get("/v1/users/" + userId)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 팔로우 테스트")
    void 유저_팔로우() throws Exception {
        Long followingId = 1L;
        FollowReq followReq = FollowReq.builder().followingId(followingId).build();
        FollowRes followRes = new FollowRes();
        when(userService.followUser(any())).thenReturn(followRes);
        this.mockMvc
                .perform(
                        post("/v1/users/follow")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(followReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
