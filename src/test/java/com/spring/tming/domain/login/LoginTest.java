package com.spring.tming.domain.login;

import static com.spring.tming.test.UserTest.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.BaseMvcTest;
import com.spring.tming.domain.user.dto.request.LoginReq;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class LoginTest extends BaseMvcTest {
    @Autowired WebApplicationContext context;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        User user =
                User.builder()
                        .userId(TEST_USER_ID)
                        .email(TEST_USER_EMAIL)
                        .username(TEST_USER_NAME)
                        .password(this.passwordEncoder.encode(TEST_USER_PASSWORD))
                        .role(TEST_USER_ROLE)
                        .job(TEST_USER_JOB)
                        .introduce(TEST_USER_INTRODUCE)
                        .profileImageUrl(TEST_USER_PROFILE_URL)
                        .build();
        userRepository.save(user);
        mockMvc =
                MockMvcBuilders.webAppContextSetup(this.context)
                        .apply(SecurityMockMvcConfigurers.springSecurity())
                        .build();
    }

    @Test
    @DisplayName("로그인_성공")
    public void login_success() throws Exception {
        LoginReq loginReq =
                LoginReq.builder().email(TEST_USER_EMAIL).password(TEST_USER_PASSWORD).build();

        mockMvc
                .perform(
                        post("/v1/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("로그인_실패")
    public void login_password_failure() throws Exception {
        LoginReq loginReq =
                LoginReq.builder().email(TEST_USER_EMAIL).password(FAILURE + TEST_USER_PASSWORD).build();

        mockMvc
                .perform(
                        post("/v1/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginReq))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(unauthenticated());
    }
}
