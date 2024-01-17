package com.spring.tming.domain.post.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.tming.domain.post.entity.Post;
import com.spring.tming.domain.post.entity.QJobLimit;
import com.spring.tming.domain.post.entity.QPost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> getAllPostByUser(Pageable pageable, String username) {
        List<Post> result =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .where(QPost.post.user.username.eq(username))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(QPost.post.createTimestamp.desc())
                        .fetch();
        long totalCount =
                jpaQueryFactory
                        .selectFrom(QPost.post)
                        .leftJoin(QPost.post.jobLimits, QJobLimit.jobLimit)
                        .fetchJoin()
                        .where(QPost.post.user.username.eq(username))
                        .fetchCount();
        return new PageImpl<>(result, pageable, totalCount);
    }
}
