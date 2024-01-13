package com.spring.tming.domain.user.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public class UserServiceMapper {

    UserServiceMapper SIGNUP = Mappers.getMapper(UserServiceMapper.class);
}
