package com.spring.tming.domain.user.controller;

import static com.spring.tming.global.meta.Job.BACKEND;
import static com.spring.tming.test.UserTest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.BaseMvcTest;
import com.spring.tming.domain.user.dto.request.*;
import com.spring.tming.domain.user.dto.response.*;
import com.spring.tming.domain.user.service.UserService;
import com.spring.tming.global.meta.Job;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest extends BaseMvcTest {
    @MockBean private UserService userService;

    @Test
    @DisplayName("회원가입")
    void 회원가입() throws Exception {

        SignupReq signupReq =
                SignupReq.builder()
                        .email(TEST_USER_EMAIL)
                        .password(TEST_USER_PASSWORD)
                        .username(TEST_USER_NAME)
                        .job(TEST_USER_JOB)
                        .build();

        SignupRes signupRes = new SignupRes();

        when(userService.signup(any())).thenReturn(signupRes);

        this.mockMvc
                .perform(
                        post("/v1/users/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signupReq)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("중복검사_닉네임")
    void 중복검사_닉네임() throws Exception {

        CheckUsernameReq checkUsernameReq = CheckUsernameReq.builder().username(TEST_USER_NAME).build();

        CheckUsernameRes checkUsernameRes = CheckUsernameRes.builder().check(true).build();

        when(userService.checkUsername(any())).thenReturn(checkUsernameRes);

        this.mockMvc
                .perform(
                        post("/v1/users/check-username")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(checkUsernameReq)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("중복검사_이메일")
    void 중복검사_이메일() throws Exception {

        CheckEmailReq checkEmailReq = CheckEmailReq.builder().email(TEST_USER_EMAIL).build();

        CheckEmailRes checkEmailRes = CheckEmailRes.builder().check(true).build();

        when(userService.checkEmail(any())).thenReturn(checkEmailRes);

        this.mockMvc
                .perform(
                        post("/v1/users/check-email")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(checkEmailReq)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

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
        when(userService.getUserProfile(any(), any())).thenReturn(userGetRes);
        this.mockMvc
                .perform(get("/v1/users/" + userId).principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
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

    @Test
    @DisplayName("유저 팔로우 취소 테스트")
    void 유저_팔로우_취소() throws Exception {
        Long followingId = 1L;
        UnfollowReq unfollowReq = UnfollowReq.builder().followingId(followingId).build();
        UnfollowRes unfollowRes = new UnfollowRes();
        when(userService.unfollowUser(any())).thenReturn(unfollowRes);
        this.mockMvc
                .perform(
                        delete("/v1/users/unfollow")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(unfollowReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 팔로워 전체 조회 테스트")
    void 유저_팔로워_전체_조회() throws Exception {
        Long userId = 1L;
        Long followerId = 2L;
        String username = "ysys";
        String profileImageUrl = "url";
        FollowerGetRes followerGetRes =
                FollowerGetRes.builder()
                        .userId(followerId)
                        .username(username)
                        .profileImageUrl(profileImageUrl)
                        .build();
        FollowerGetResList followerGetResList =
                FollowerGetResList.builder().followers(List.of(followerGetRes)).total(1).build();
        when(userService.getFollowers(any())).thenReturn(followerGetResList);
        this.mockMvc
                .perform(get("/v1/users/follower/" + userId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 팔로잉 전체 조회 테스트")
    void 유저_팔로잉_전체_조회() throws Exception {
        Long userId = 1L;
        Long followerId = 2L;
        String username = "ysys";
        String profileImageUrl = "url";
        FollowingGetRes followingGetRes =
                FollowingGetRes.builder()
                        .userId(followerId)
                        .username(username)
                        .profileImageUrl(profileImageUrl)
                        .build();
        FollowingGetResList followerGetResList =
                FollowingGetResList.builder().followings(List.of(followingGetRes)).total(1).build();
        when(userService.getFollowings(any())).thenReturn(followerGetResList);
        this.mockMvc
                .perform(get("/v1/users/following/" + userId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 수정 테스트")
    void 유저_수정() throws Exception {
        String password = "Qwer1234!";
        String username = "ysys";
        Job job = BACKEND;
        String introduce = "intro";
        UserUpdateReq userUpdateReq =
                UserUpdateReq.builder()
                        .password(password)
                        .username(username)
                        .job(job)
                        .introduce(introduce)
                        .build();
        String imageUrl = "images/sparta.png";
        Resource fileResource = new ClassPathResource(imageUrl);
        MockMultipartFile file =
                new MockMultipartFile(
                        "image",
                        fileResource.getFilename(),
                        IMAGE_JPEG.getType(),
                        fileResource.getInputStream());
        MockMultipartFile multipartFile =
                new MockMultipartFile("multipartFile", "orig", "multipart/form-data", file.getBytes());
        String json = objectMapper.writeValueAsString(userUpdateReq);
        MockMultipartFile req =
                new MockMultipartFile(
                        "userUpdateReq", "json", "application/json", json.getBytes(StandardCharsets.UTF_8));
        UserUpdateRes userUpdateRes = new UserUpdateRes();
        when(userService.updateUser(any(), any())).thenReturn(userUpdateRes);
        this.mockMvc
                .perform(
                        multipart(PATCH, "/v1/users")
                                .file(multipartFile)
                                .file(req)
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
