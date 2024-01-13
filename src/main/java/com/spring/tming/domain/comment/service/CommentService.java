package com.spring.tming.domain.comment.service;

import com.spring.tming.domain.comment.dto.request.CommentDeleteReq;
import com.spring.tming.domain.comment.dto.request.CommentSaveReq;
import com.spring.tming.domain.comment.dto.request.CommentUpdateReq;
import com.spring.tming.domain.comment.dto.response.CommentDeleteRes;
import com.spring.tming.domain.comment.dto.response.CommentGetRes;
import com.spring.tming.domain.comment.dto.response.CommentGetResList;
import com.spring.tming.domain.comment.dto.response.CommentSaveRes;
import com.spring.tming.domain.comment.dto.response.CommentUpdateRes;
import com.spring.tming.domain.comment.entity.Comment;
import com.spring.tming.domain.comment.repository.CommentRepository;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.repository.PostRepository;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.validator.CommentValidator;
import com.spring.tming.global.validator.PostValidator;
import com.spring.tming.global.validator.UserValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentSaveRes saveComment(CommentSaveReq commentSaveReq) {
        User user = getUserByUserId(commentSaveReq.getUserId());
        Post post = getPostByPostId(commentSaveReq.getPostId());
        return CommentServiceMapper.INSTANCE.toCommentSaveRes(
                commentRepository.save(
                        Comment.builder().content(commentSaveReq.getContent()).user(user).post(post).build()));
    }

    @Transactional
    public CommentUpdateRes updateComment(CommentUpdateReq commentUpdateReq) {
        Comment comment =
                commentRepository.findByCommentIdAndUserUserId(
                        commentUpdateReq.getCommentId(), commentUpdateReq.getUserId());
        CommentValidator.validate(comment);
        commentRepository.save(
                Comment.builder()
                        .commentId(comment.getCommentId())
                        .content(commentUpdateReq.getContent())
                        .user(comment.getUser())
                        .post(comment.getPost())
                        .build());
        return new CommentUpdateRes();
    }

    @Transactional
    public CommentDeleteRes deleteComment(CommentDeleteReq commentDeleteReq) {
        Comment comment =
                commentRepository.findByCommentIdAndUserUserId(
                        commentDeleteReq.getCommentId(), commentDeleteReq.getUserId());
        CommentValidator.validate(comment);
        commentRepository.delete(comment);
        return new CommentDeleteRes();
    }

    @Transactional(readOnly = true)
    public CommentGetResList getComments(Long postId) {
        List<CommentGetRes> commentGetResList =
                CommentServiceMapper.INSTANCE.toCommentGetResList(
                        commentRepository.findByPostPostId(postId));
        return CommentGetResList.builder()
                .comments(commentGetResList)
                .total(commentGetResList.size())
                .build();
    }

    private User getUserByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        UserValidator.validate(user);
        return user;
    }

    private Post getPostByPostId(Long postId) {
        Post post = postRepository.findByPostId(postId);
        PostValidator.checkIsNullPost(post);
        return post;
    }
}
