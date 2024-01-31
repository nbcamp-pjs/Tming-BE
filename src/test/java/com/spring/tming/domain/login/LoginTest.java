package com.spring.tming.domain.login;

import static com.spring.tming.test.UserTest.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired WebApplicationContext context;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;
    private MockMvc mockMvc;

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
    public void login_success() throws Exception {

        String jsonReq =
                "{\"email\": \"" + TEST_USER_EMAIL + "\",\"password\": \"" + TEST_USER_PASSWORD + "\"}";

        mockMvc
                .perform(post("/v1/users/login").contentType(MediaType.APPLICATION_JSON).content(jsonReq))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void login_password_failure() throws Exception {
        String email = TEST_USER_EMAIL;
        String password = FAILURE + TEST_USER_PASSWORD;

        String jsonReq = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";

        mockMvc
                .perform(post("/v1/users/login").contentType(MediaType.APPLICATION_JSON).content(jsonReq))
                .andDo(print())
                .andExpect(unauthenticated());
    }
}
