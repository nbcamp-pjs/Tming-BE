package com.spring.tming.domain.post.service;

import com.spring.tming.domain.post.dto.response.PostCreateRes;
import com.spring.tming.domain.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostServiceMapper {
    PostServiceMapper INSTANCE = Mappers.getMapper(PostServiceMapper.class);

    PostCreateRes toPostCreateRes(Post post);
}
