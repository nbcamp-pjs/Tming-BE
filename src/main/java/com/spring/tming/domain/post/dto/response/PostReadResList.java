package com.spring.tming.domain.post.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReadResList {
    private List<PostAllReadRes> postAllReadRes;
    private Long totalCount;
    private Integer totalPage;
    private Integer pageNumber;

    @Builder
    private PostReadResList(
            List<PostAllReadRes> postAllReadRes, Long totalCount, Integer totalPage, Integer pageNumber) {
        this.postAllReadRes = postAllReadRes;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pageNumber = pageNumber;
    }
}
