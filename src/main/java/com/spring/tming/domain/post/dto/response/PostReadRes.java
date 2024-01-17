package com.spring.tming.domain.post.dto.response;

import com.spring.tming.global.meta.Skill;
import com.spring.tming.global.meta.Status;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReadRes {
    private Long postId;
    private String title;
    private String content;
    private String deadline;
    private Long visit;
    private Long like;
    private String imageUrl;
    private Status status;
    private String username;
    private List<PostJobLimitRes> jobLimits;
    private List<Skill> skills;
    //	private List<Member> members;

    @Builder
    private PostReadRes(
            Long postId,
            String title,
            String content,
            String deadline,
            Long visit,
            Long like,
            String imageUrl,
            Status status,
            String username,
            List<PostJobLimitRes> jobLimits,
            List<Skill> skills
            //            List<Member> members;
            ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.visit = visit;
        this.like = like;
        this.imageUrl = imageUrl;
        this.status = status;
        this.username = username;
        this.jobLimits = jobLimits;
        this.skills = skills;
        //        this.members = member;
    }
}
