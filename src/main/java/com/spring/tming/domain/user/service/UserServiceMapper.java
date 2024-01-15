package com.spring.tming.domain.user.service;

import com.spring.tming.domain.user.dto.response.LoginRes;
import com.spring.tming.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserServiceMapper {

    UserServiceMapper USER_SERVICE_MAPPER = Mappers.getMapper(UserServiceMapper.class);

    LoginRes toLoginRes(User user);
}
