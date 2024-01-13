package com.spring.tming.domain.comment.service;

import com.spring.tming.domain.comment.dto.response.CommentGetRes;
import com.spring.tming.domain.comment.dto.response.CommentSaveRes;
import com.spring.tming.domain.comment.entity.Comment;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentServiceMapper {
    CommentServiceMapper INSTANCE = Mappers.getMapper(CommentServiceMapper.class);

    @Mapping(source = "createTimestamp", target = "createTimestamp")
    default String toCreateTimeStamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        TimeZone koreaTimeZone = TimeZone.getTimeZone("Asia/Seoul");
        timestamp.setTime(timestamp.getTime() + koreaTimeZone.getRawOffset());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }

    @Mapping(source = "createTimestamp", target = "createTimestamp")
    @Mapping(source = "user.username", target = "username")
    CommentGetRes toCommentGetRes(Comment comment);

    List<CommentGetRes> toCommentGetResList(List<Comment> comments);

    CommentSaveRes toCommentSaveRes(Comment comment);
}
