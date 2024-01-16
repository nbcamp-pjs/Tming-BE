package com.spring.tming.domain.user.service;

import com.spring.tming.domain.user.dto.response.FollowerGetRes;
import com.spring.tming.domain.user.dto.response.FollowingGetRes;
import com.spring.tming.domain.user.dto.response.UserGetRes;
import com.spring.tming.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserServiceMapper {

    UserServiceMapper INSTANCE = Mappers.getMapper(UserServiceMapper.class);

    // TODO add follower, following cnt
    @Mapping(source = "job.description", target = "job")
    UserGetRes toUserGetRes(User user);

    List<FollowerGetRes> toFollowerGetResList(List<User> users);

    List<FollowingGetRes> toFollowingGetResList(List<User> users);
}
