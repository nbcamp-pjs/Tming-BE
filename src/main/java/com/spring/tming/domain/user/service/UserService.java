package com.spring.tming.domain.user.service;

import static com.spring.tming.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;
import static com.spring.tming.global.meta.ResultCode.SYSTEM_ERROR;

import com.spring.tming.domain.user.dto.request.CheckEmailReq;
import com.spring.tming.domain.user.dto.request.CheckUsernameReq;
import com.spring.tming.domain.user.dto.request.FollowReq;
import com.spring.tming.domain.user.dto.request.SignupReq;
import com.spring.tming.domain.user.dto.request.UnfollowReq;
import com.spring.tming.domain.user.dto.request.UserUpdateReq;
import com.spring.tming.domain.user.dto.response.*;
import com.spring.tming.domain.user.entity.Follow;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.FollowRepository;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.entity.Role;
import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.redis.RedisUtil;
import com.spring.tming.global.s3.S3Provider;
import com.spring.tming.global.validator.EmailCheckValidator;
import com.spring.tming.global.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Provider s3Provider;
    private final RedisUtil redisUtil;

    private final String FOLDER_USER = "user";

    @Value("${cloud.aws.s3.bucket.url}")
    private String bucketUrl;

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

    @Transactional
    public CheckUsernameRes checkUsername(CheckUsernameReq checkUsernameReq) {
        User checkUser = userRepository.findByUsername(checkUsernameReq.getUsername());
        return CheckUsernameRes.builder().check(checkUser == null).build();
    }

    @Transactional
    public CheckEmailRes checkEmail(CheckEmailReq checkEmailReq) {
        User checkUser = userRepository.findByEmail(checkEmailReq.getEmail());
        return CheckEmailRes.builder().check(checkUser == null).build();
    }

    @Transactional(readOnly = true)
    public UserGetRes getUserProfile(Long userId, Long myUserId) {
        User user = getUserByUserId(userId);
        return UserServiceMapper.INSTANCE.toUserGetRes(user, myUserId);
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

    @Transactional(readOnly = true)
    public FollowerGetResList getFollowers(Long userId) {
        List<FollowerGetRes> followerGetReses =
                UserServiceMapper.INSTANCE.toFollowerGetResList(userRepository.findFollowers(userId));
        return FollowerGetResList.builder()
                .followers(followerGetReses)
                .total(followerGetReses.size())
                .build();
    }

    @Transactional(readOnly = true)
    public FollowingGetResList getFollowings(Long userId) {
        List<FollowingGetRes> followingGetReses =
                UserServiceMapper.INSTANCE.toFollowingGetResList(userRepository.findFollowings(userId));
        return FollowingGetResList.builder()
                .followings(followingGetReses)
                .total(followingGetReses.size())
                .build();
    }

    @Transactional
    public UserUpdateRes updateUser(UserUpdateReq userUpdateReq, MultipartFile multipartFile) {
        UserValidator.validate(userUpdateReq);
        User prevUser = getUserByUserId(userUpdateReq.getUserId());
        checkDuplicateUsername(userUpdateReq.getUsername());
        if (isExistProfileImageUrl(prevUser.getProfileImageUrl())) {
            try {
                s3Provider.deleteImage(prevUser.getProfileImageUrl());
            } catch (Exception e) {
                // 버그로 인해 image url이 없는 경우
                log.warn(e.getMessage());
            }
        }

        String profileImageUrl = saveFile(multipartFile).replace(bucketUrl, "");
        userRepository.save(
                User.builder()
                        .userId(prevUser.getUserId())
                        .email(prevUser.getEmail())
                        .password(passwordEncoder.encode(userUpdateReq.getPassword()))
                        .username(userUpdateReq.getUsername())
                        .job(userUpdateReq.getJob())
                        .introduce(userUpdateReq.getIntroduce())
                        .profileImageUrl(profileImageUrl)
                        .build());
        return new UserUpdateRes();
    }

    private boolean isExistProfileImageUrl(String profileImageUrl) {
        return profileImageUrl != null;
    }

    private String saveFile(MultipartFile multipartFile) {
        try {
            return s3Provider.saveFile(multipartFile, FOLDER_USER);
        } catch (Exception e) {
            throw new GlobalException(SYSTEM_ERROR);
        }
    }

    private void checkDuplicateUsername(String username) {
        UserValidator.duplicatedUser(userRepository.findByUsername(username));
    }

    private User getUserByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        UserValidator.validate(user);
        return user;
    }

    public LogoutRes logout(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER).split(" ")[1].trim();
        redisUtil.delete(refreshToken);
        return new LogoutRes();
    }
}
