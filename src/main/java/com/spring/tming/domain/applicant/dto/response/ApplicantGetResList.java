package com.spring.tming.domain.applicant.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantGetResList {
    private List<ApplicantGetRes> applicants;
    private int total;

    @Builder
    private ApplicantGetResList(List<ApplicantGetRes> applicants, int total) {
        this.applicants = applicants;
        this.total = total;
    }
}
