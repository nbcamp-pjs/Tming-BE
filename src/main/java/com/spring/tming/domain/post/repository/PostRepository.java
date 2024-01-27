package com.spring.tming.domain.post.repository;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.repository.custom.PostRepositoryCustom;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.meta.Status;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Post findByPostId(Long postId);

    Post findByPostIdAndUser(Long postId, User user);

    List<Post> findAllByDeadlineBeforeAndStatus(Timestamp currentSeoulInstant, Status open);
}
