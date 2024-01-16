package com.spring.tming.domain.user.repository;

import com.spring.tming.domain.user.entity.Follow;
import com.spring.tming.domain.user.entity.FollowId;
import com.spring.tming.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    Follow findByFollowerAndFollowing(User follower, User following);
}
