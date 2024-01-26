package com.spring.tming.domain.post.service;

import com.spring.tming.domain.members.entity.Member;
import com.spring.tming.domain.post.dto.response.PostAllReadRes;
import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.dto.response.PostJobLimitRes;
import com.spring.tming.domain.post.dto.response.PostMemberRes;
import com.spring.tming.domain.post.dto.response.PostReadRes;
import com.spring.tming.domain.post.entity.JobLimit;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostLike;
import com.spring.tming.domain.post.entity.PostStack;
import com.spring.tming.global.meta.Status;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

@Mapper
public interface PostServiceMapper {
    PostServiceMapper INSTANCE = Mappers.getMapper(PostServiceMapper.class);

    @Mappings({
        @Mapping(source = "deadline", target = "deadline"),
        @Mapping(source = "createTimestamp", target = "createTimestamp")
    })
    default String toStringTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        LocalDateTime localDateTime =
                timestamp.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Mapping(source = "status", target = "status")
    default String toStringStatus(Status status) {
        return status.getDescription();
    }

    @Mapping(source = "postLikes", target = "like")
    default Long toLongLike(List<PostLike> postLikes) {
        return (long) postLikes.size();
    }

    @Mapping(source = "postStacks", target = "skills")
    default List<String> toSkillList(Set<PostStack> postStacks) {
        if (CollectionUtils.isEmpty(postStacks)) {
            return null;
        }
        List<String> skills = new ArrayList<>();
        postStacks.forEach(postStack -> skills.add(postStack.getSkill().getDescription()));
        return skills;
    }

    @Named("isCheckLiked")
    default boolean isCheckLiked(List<PostLike> postLikes, @Context Long userId) {
        if (CollectionUtils.isEmpty(postLikes)) {
            return false;
        }

        return postLikes.stream().anyMatch(postLike -> userId.equals(postLike.getUser().getUserId()));
    }

    PostCreateRes toPostCreateRes(Post post);

    List<PostJobLimitRes> toPostJobLimitResList(List<JobLimit> jobLimits);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.profileImageUrl", target = "profileImageUrl")
    PostMemberRes toPostMemberRes(Member member);

    List<PostMemberRes> toPostMemberResList(List<Member> members);

    @Mapping(source = "deadline", target = "deadline")
    @Mapping(source = "createTimestamp", target = "createTimestamp")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "postLikes", target = "like")
    @Mapping(source = "post.user.username", target = "username")
    @Mapping(source = "postStacks", target = "skills")
    @Mapping(source = "post.postLikes", qualifiedByName = "isCheckLiked", target = "liked")
    PostReadRes toPostReadRes(Post post, @Context Long userId);

    @Mapping(source = "deadline", target = "deadline")
    @Mapping(source = "createTimestamp", target = "createTimestamp")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "postLikes", target = "like")
    @Mapping(source = "post.user.username", target = "username")
    PostAllReadRes toPostAllReadResList(Post post);

    List<PostAllReadRes> toPostAllReadResList(List<Post> posts);
}
