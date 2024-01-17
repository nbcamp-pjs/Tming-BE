package com.spring.tming.domain.post.repository.custom;

import com.spring.tming.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> getAllPostByUser(Pageable pageable, String username);
}
