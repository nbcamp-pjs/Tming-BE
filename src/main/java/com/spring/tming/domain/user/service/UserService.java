package com.spring.tming.domain.user.service;

import com.spring.tming.domain.user.dto.request.SignupReq;
import com.spring.tming.domain.user.dto.response.SignupRes;
import com.spring.tming.domain.user.dto.response.UserGetRes;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.entity.Role;
import com.spring.tming.global.validator.EmailCheckValidator;
import com.spring.tming.global.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupRes signup(SignupReq signupReq) {
        UserValidator.validate(signupReq);

        // TODO : 이메일 인증확인 추가해야됨
        EmailCheckValidator.validateEmail(signupReq.getEmail());

        User checkuser =
                userRepository.findByEmailOrUsername(signupReq.getEmail(), signupReq.getUsername());
        UserValidator.duplicatedUser(checkuser);

        String password = passwordEncoder.encode(signupReq.getPassword());

        User user =
                User.builder()
                        .email(signupReq.getEmail())
                        .password(password)
                        .username(signupReq.getUsername())
                        .role(Role.USER)
                        .job(signupReq.getJob())
                        .build();
        userRepository.save(user);
        return new SignupRes();
    }

    @Transactional(readOnly = true)
    public UserGetRes getUserProfile(Long userId) {
        return UserServiceMapper.INSTANCE.toUserGetRes(getUserByUserId(userId));
    }

    private User getUserByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        UserValidator.validate(user);
        return user;
    }
}
