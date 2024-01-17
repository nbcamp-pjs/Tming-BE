package com.spring.tming.domain.post.repository.custom;

import static com.spring.tming.global.meta.ResultCode.POST_INVALID_FILTER;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.QJobLimit;
import com.spring.tming.domain.post.entity.QPost;
import com.spring.tming.domain.post.entity.QPostStack;
import com.spring.tming.global.exception.GlobalException;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> getAllPost(PageRequest pageRequest) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .fetchCount();
        return new PageImpl<>(result, pageRequest, totalCount);
    }

    @Override
    public Page<Post> getAllPostByUser(String username, PageRequest pageRequest) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .where(QPost.post.user.username.eq(username))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .where(QPost.post.user.username.eq(username))
                        .fetchCount();
        return new PageImpl<>(result, pageRequest, totalCount);
    }

    @Override
    public Page<Post> getAllPostBySkill(Skill skill, PageRequest pageRequest) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.postStacks, QPostStack.postStack)
                        .fetchJoin()
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .where(skillEq(skill))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.postStacks, QPostStack.postStack)
                        .fetchJoin()
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .where(skillEq(skill))
                        .fetchCount();
        return new PageImpl<>(result, pageRequest, totalCount);
    }

    @Override
    public Page<Post> getAllPostByJob(Job job, PageRequest pageRequest) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .where(jobEq(job))
                        .offset(pageRequest.getOffset())
                        .limit(pageRequest.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .where(jobEq(job))
                        .fetchCount();
        return new PageImpl<>(result, pageRequest, totalCount);
    }

    private BooleanExpression skillEq(Skill skill) {
        if (skill == null) {
            throw new GlobalException(POST_INVALID_FILTER);
        }
        return QPostStack.postStack.skill.eq(skill);
    }

    private BooleanExpression jobEq(Job job) {
        if (job == null) {
            throw new GlobalException(POST_INVALID_FILTER);
        }
        return QJobLimit.jobLimit.job.eq(job);
    }
}
