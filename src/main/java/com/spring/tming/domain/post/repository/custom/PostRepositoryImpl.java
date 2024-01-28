package com.spring.tming.domain.post.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.tming.domain.applicant.entity.QApplicant;
import com.spring.tming.domain.members.entity.QMember;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.QPost;
import com.spring.tming.domain.post.entity.QPostLike;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.global.meta.Job;
import com.spring.tming.global.meta.Skill;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getAllPost(Skill skill, Job job, PageRequest pageRequest) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .fetch()
                        .size();
        return new PageImpl<>(result, pageRequest, totalCount);
    }

    @Override
    public Page<Post> getAllPostByLike(User user, Skill skill, Job job, PageRequest pageRequest) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .innerJoin(QPost.post.postLikes, QPostLike.postLike)
                        .fetchJoin()
                        .where(QPostLike.postLike.user.eq(user))
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .innerJoin(QPost.post.postLikes, QPostLike.postLike)
                        .fetchJoin()
                        .where(QPostLike.postLike.user.eq(user))
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .fetch()
                        .size();
        return new PageImpl<>(result, pageRequest, totalCount);
    }

    @Override
    public Page<Post> getAllPostByApply(User user, Skill skill, Job job, PageRequest pageRequest) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.applicants, QApplicant.applicant)
                        .fetchJoin()
                        .where(QApplicant.applicant.user.eq(user))
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.applicants, QApplicant.applicant)
                        .fetchJoin()
                        .where(QApplicant.applicant.user.eq(user))
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .fetch()
                        .size();
        return new PageImpl<>(result, pageRequest, totalCount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getAllPostByUser(User user, Skill skill, Job job, PageRequest pageRequest) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .where(QPost.post.user.username.eq(user.getUsername()))
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .where(QPost.post.user.username.eq(user.getUsername()))
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .fetch()
                        .size();
        return new PageImpl<>(result, pageRequest, totalCount);
    }

    @Override
    public Page<Post> getAllPostByMember(User user, Skill skill, Job job, PageRequest pageRequest) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.members, QMember.member)
                        .fetchJoin()
                        .where(QMember.member.user.eq(user))
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.members, QMember.member)
                        .fetchJoin()
                        .where(QMember.member.user.eq(user))
                        .where(skillEq(skill))
                        .where(jobIn(job))
                        .fetch()
                        .size();
        return new PageImpl<>(result, pageRequest, totalCount);
    }

    private BooleanExpression skillEq(Skill skill) {
        if (skill == null) {
            return null;
        }
        return QPost.post.postStacks.any().skill.eq(skill);
    }

    private BooleanExpression jobIn(Job job) {
        if (job == null) {
            return null;
        }
        return QPost.post.jobLimits.any().job.eq(job);
    }
}
