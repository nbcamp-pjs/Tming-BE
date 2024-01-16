package com.spring.tming.domain.user.service;

import com.spring.tming.domain.user.dto.response.FollowerGetRes;
import com.spring.tming.domain.user.dto.response.FollowingGetRes;
import com.spring.tming.domain.user.dto.response.LoginRes;
import com.spring.tming.domain.user.dto.response.UserGetRes;
import com.spring.tming.domain.user.entity.Follow;
import com.spring.tming.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

@Mapper
public interface UserServiceMapper {

    UserServiceMapper INSTANCE = Mappers.getMapper(UserServiceMapper.class);

    @Mapping(source = "followings", target = "following")
    @Mapping(source = "followers", target = "follower")
    default int toFollowCnt(List<Follow> follows) {
        if (CollectionUtils.isEmpty(follows)) {
            return 0;
        }
        return follows.size();
    }

    @Mapping(source = "followers", target = "follower")
    @Mapping(source = "followings", target = "following")
    @Mapping(source = "job.description", target = "job")
    UserGetRes toUserGetRes(User user);

    LoginRes toLoginRes(User user);

    List<FollowerGetRes> toFollowerGetResList(List<User> users);

    List<FollowingGetRes> toFollowingGetResList(List<User> users);
}
