package com.spring.tming.domain.post.service;

import com.spring.tming.domain.post.dto.PostJobLimitDto;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.dto.response.PostReadRes;
import com.spring.tming.domain.post.entity.JobLimit;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostStack;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

@Mapper
public interface PostServiceMapper {
    PostServiceMapper INSTANCE = Mappers.getMapper(PostServiceMapper.class);

    @Mapping(source = "deadline", target = "deadline")
    default String toStringTimestamp(Timestamp deadline) {
        if (deadline == null) {
            return null;
        }
        LocalDateTime localDateTime =
                deadline.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Mapping(source = "postStacks", target = "skills")
    default List<String> toSkillList(List<PostStack> postStacks) {
        if (CollectionUtils.isEmpty(postStacks)) {
            return null;
        }
        List<String> skills = new ArrayList<>();
        postStacks.forEach(postStack -> skills.add(postStack.getSkill().getDescription()));
        return skills;
    }

    @Mapping(source = "jobLimits", target = "jobLimits")
    default List<PostJobLimitDto> toPostJobLimitRes(List<JobLimit> jobLimits) {
        if (CollectionUtils.isEmpty(jobLimits)) {
            return null;
        }
        List<PostJobLimitDto> postJobLimitRes = new ArrayList<>();
        jobLimits.forEach(
                jobLimit -> postJobLimitRes.add(PostServiceMapper.INSTANCE.toPostjobLimitDto(jobLimit)));
        return postJobLimitRes;
    }

    PostCreateRes toPostCreateRes(Post post);

    PostJobLimitDto toPostjobLimitDto(JobLimit jobLimit);

    @Mapping(source = "deadline", target = "deadline")
    @Mapping(source = "post.user.username", target = "username")
    @Mapping(source = "postStacks", target = "skills")
    @Mapping(source = "jobLimits", target = "jobLimits")
    PostReadRes toPostReadRes(Post post);

    @Mapping(source = "deadline", target = "deadline")
    @Mapping(source = "post.user.username", target = "username")
    @Mapping(source = "jobLimits", target = "jobLimits")
    List<PostReadRes> toPostReadResList(List<Post> posts);
}
