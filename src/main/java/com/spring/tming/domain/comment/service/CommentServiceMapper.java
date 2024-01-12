package com.spring.tming.domain.comment.service;

import com.spring.tming.domain.comment.dto.response.CommentSaveRes;
import com.spring.tming.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentServiceMapper {
    CommentServiceMapper INSTANCE = Mappers.getMapper(CommentServiceMapper.class);

    CommentSaveRes toCommentSaveRes(Comment comment);
}
