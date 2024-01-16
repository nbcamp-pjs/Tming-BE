package com.spring.tming.domain.user.service;

import com.spring.tming.domain.user.dto.request.FollowReq;
import com.spring.tming.domain.user.dto.request.SignupReq;
import com.spring.tming.domain.user.dto.request.UnfollowReq;
import com.spring.tming.domain.user.dto.response.FollowRes;
import com.spring.tming.domain.user.dto.response.SignupRes;
import com.spring.tming.domain.user.dto.response.UnfollowRes;
import com.spring.tming.domain.user.dto.response.UserGetRes;
import com.spring.tming.domain.user.entity.Follow;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.FollowRepository;
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
    private final FollowRepository followRepository;
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

    @Transactional
    public FollowRes followUser(FollowReq followReq) {
        User follower = getUserByUserId(followReq.getFollowerId());
        User following = getUserByUserId(followReq.getFollowingId());
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following);
        UserValidator.checkAlreadyFollowed(follow);
        followRepository.save(Follow.builder().follower(follower).following(following).build());
        return new FollowRes();
    }

    @Transactional
    public UnfollowRes unfollowUser(UnfollowReq unfollowReq) {
        User follower = getUserByUserId(unfollowReq.getFollowerId());
        User following = getUserByUserId(unfollowReq.getFollowingId());
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following);
        UserValidator.checkNotYetFollowed(follow);
        followRepository.delete(follow);
        return new UnfollowRes();
    }

    private User getUserByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        UserValidator.validate(user);
        return user;
    }
}
