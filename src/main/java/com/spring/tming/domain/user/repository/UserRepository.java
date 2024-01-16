package com.spring.tming.domain.user.repository;

import com.spring.tming.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUserId(Long userId);

    User findByEmailOrUsername(String email, String username);

    User findByUsername(String username);

    @Query(
            value =
                    "select u from User u "
                            + "join Follow f on f.follower.userId=u.userId "
                            + "where u.userId=:userId")
    List<User> findFollowers(Long userId);

    @Query(
            value =
                    "select u from User u "
                            + "join Follow f on f.following.userId=u.userId "
                            + "where u.userId=:userId")
    List<User> findFollowings(Long userId);
}
