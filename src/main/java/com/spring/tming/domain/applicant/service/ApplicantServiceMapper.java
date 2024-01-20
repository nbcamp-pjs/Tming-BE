package com.spring.tming.domain.applicant.service;

import com.spring.tming.domain.applicant.dto.response.ApplicantGetRes;
import com.spring.tming.domain.applicant.entity.Applicant;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApplicantServiceMapper {
    ApplicantServiceMapper INSTANCE = Mappers.getMapper(ApplicantServiceMapper.class);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "job.description", target = "job")
    @Mapping(source = "user.profileImageUrl", target = "profileImageUrl")
    ApplicantGetRes toApplicantGetRes(Applicant applicant);

    List<ApplicantGetRes> toApplicantGetResList(List<Applicant> applicants);
}
