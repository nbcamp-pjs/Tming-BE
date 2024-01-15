package com.spring.tming.domain.post.repository;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.PostLike;
import com.spring.tming.domain.post.entity.PostLikeId;
import com.spring.tming.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    PostLike findByUserAndPost(User user, Post post);
}
