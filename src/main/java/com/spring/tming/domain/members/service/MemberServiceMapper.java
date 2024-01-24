package com.spring.tming.domain.members.service;

import com.spring.tming.domain.members.dto.response.MemberGetRes;
import com.spring.tming.domain.members.entity.Member;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberServiceMapper {
    MemberServiceMapper INSTANCE = Mappers.getMapper(MemberServiceMapper.class);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "job.description", target = "job")
    @Mapping(source = "user.profileImageUrl", target = "profileImageUrl")
    MemberGetRes toMemberGetRes(Member member);

    List<MemberGetRes> toMemberGetResList(List<Member> members);
}
