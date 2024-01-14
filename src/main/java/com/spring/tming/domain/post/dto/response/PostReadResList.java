package com.spring.tming.domain.post.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReadResList {
    private List<PostReadRes> postReadRes;

    @Builder
    private PostReadResList(List<PostReadRes> postReadRes) {
        this.postReadRes = postReadRes;
    }
}
