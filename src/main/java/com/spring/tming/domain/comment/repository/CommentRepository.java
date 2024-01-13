package com.spring.tming.domain.comment.repository;

import com.spring.tming.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentIdAndUserUserId(Long commentId, Long userId);

    List<Comment> findByPostPostId(Long postId);
}
