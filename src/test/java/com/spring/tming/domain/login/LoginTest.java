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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class LoginTest extends BaseMvcTest {
    @Autowired WebApplicationContext context;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        User TEST_USER =
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
        userRepository.save(TEST_USER);
        mockMvc =
                MockMvcBuilders.webAppContextSetup(this.context)
                        .apply(SecurityMockMvcConfigurers.springSecurity())
                        .build();
    }

    @Test
    public void login_success() throws Exception {
        LoginReq req = LoginReq.builder().email(TEST_USER_EMAIL).password(TEST_USER_PASSWORD).build();

        mockMvc
                .perform(
                        post("/v1/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void login_password_failure() throws Exception {
        LoginReq req =
                LoginReq.builder().email(TEST_USER_EMAIL).password(FAILURE + TEST_USER_PASSWORD).build();

        mockMvc
                .perform(
                        post("/v1/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                                .principal(this.mockPrincipal))
                .andDo(print())
                .andExpect(unauthenticated());
    }
}
