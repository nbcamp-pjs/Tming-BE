package com.spring.tming.domain.user.service;

import com.spring.tming.domain.post.entity.Job;
import com.spring.tming.domain.user.dto.request.SignupReq;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.entity.Role;
import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;
import com.spring.tming.global.validator.EmailCheckValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Void signup(SignupReq signupReq) {
        String email = signupReq.getEmail();
        EmailCheckValidator.validateEmail(email);

        String password = passwordEncoder.encode(signupReq.getPassword());

        Role role = Role.USER;
        if (signupReq.isRole()) {
            role = Role.ADMIN;
        }

        Job job = setJob(signupReq.getJob());

        User user =
                User.builder()
                        .email(email)
                        .password(password)
                        .username(signupReq.getUsername())
                        .role(role)
                        .job(job)
                        .introduce(signupReq.getIntroduce())
                        .build();
        userRepository.save(user);
        return null;
    }

    private Job setJob(String job) {
        return switch (job) {
            case "1" -> Job.FRONTEND;
            case "2" -> Job.BACKEND;
            case "3" -> Job.FULLSTACK;
            case "4" -> Job.MOBILE;
            case "5" -> Job.DEVOPS;
            case "6" -> Job.QA;
            case "7" -> Job.DATA;
            case "8" -> Job.AI_ML;
            case "9" -> Job.SECURITY;
            case "10" -> Job.UI_UX;
            case "11" -> Job.CLOUD;
            case "12" -> Job.SOFTWARE_ARCHITECT;
            case "13" -> Job.TECH_LEAD;
            case "14" -> Job.MANAGER;
            default -> throw new GlobalException(ResultCode.JOB_MISMATCH);
        };
    }
}
