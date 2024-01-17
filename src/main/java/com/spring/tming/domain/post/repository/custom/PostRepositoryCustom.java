package com.spring.tming.domain.post.repository.custom;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.global.meta.Job;
import com.spring.tming.global.meta.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PostRepositoryCustom {

    Page<Post> getAllPost(PageRequest pageRequest);

    Page<Post> getAllPostByUser(String username, PageRequest pageRequest);

    Page<Post> getAllPostBySkill(Skill skill, PageRequest pageRequest);

    Page<Post> getAllPostByJob(Job job, PageRequest pageRequest);
}
