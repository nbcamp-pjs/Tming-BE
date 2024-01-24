package com.spring.tming.domain.members.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGetResList {
    private List<MemberGetRes> members;
    private int total;

    @Builder
    private MemberGetResList(List<MemberGetRes> members, int total) {
        this.members = members;
        this.total = total;
    }
}
