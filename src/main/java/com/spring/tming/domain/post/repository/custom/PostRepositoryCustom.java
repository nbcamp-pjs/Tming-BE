package com.spring.tming.domain.post.repository.custom;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.global.meta.Job;
import com.spring.tming.global.meta.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> getAllPost(Pageable pageable);

    Page<Post> getAllPostByUser(String username, Pageable pageable);

    Page<Post> getAllPostBySkill(Skill skill, Pageable pageable);

    Page<Post> getAllPostByJob(Job job, Pageable pageable);
}
