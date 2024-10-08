package com.spring.tming.domain.post.repository.custom;

import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.meta.Job;
import com.spring.tming.global.meta.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PostRepositoryCustom {

    Page<Post> getAllPost(Skill skill, Job job, PageRequest pageRequest);

    Page<Post> getAllPostByLike(User user, Skill skill, Job job, PageRequest pageRequest);

    Page<Post> getAllPostByApply(User user, Skill skill, Job job, PageRequest pageRequest);

    Page<Post> getAllPostByUser(User user, Skill skill, Job job, PageRequest pageRequest);

    Page<Post> getAllPostByMember(User user, Skill skill, Job job, PageRequest pageRequest);

    //    Page<Post> getAllPostBySkill(Skill skill, PageRequest pageRequest);
    //
    //    Page<Post> getAllPostByJob(Job job, PageRequest pageRequest);
}
