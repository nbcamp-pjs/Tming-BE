package com.spring.tming.domain.post.dto.request;

import com.spring.tming.global.meta.Job;
import com.spring.tming.global.meta.Skill;
import com.spring.tming.global.meta.Type;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReadReq {
    private Type type;
    private Skill skill;
    private Job job;
    private PageRequest pageRequest;

    @Builder
    private PostReadReq(Type type, Skill skill, Job job, PageRequest pageRequest) {
        this.type = type;
        this.skill = skill;
        this.job = job;
        this.pageRequest = pageRequest;
    }
}
