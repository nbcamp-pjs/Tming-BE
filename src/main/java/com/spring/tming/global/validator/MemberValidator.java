package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_MEMBER;

import com.spring.tming.domain.members.entity.Member;
import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;

public class MemberValidator {

    public static void validate(Member member) {
        if (isNullMember(member)) {
            throw new GlobalException(NOT_FOUND_MEMBER);
        }
    }

    public static void checkAlreadyAdmitted(Member member) {
        if (isExistMember(member)) {
            throw new GlobalException(ResultCode.ALREADY_ADMITTED_MEMBER);
        }
    }

    private static boolean isExistMember(Member member) {
        return member != null;
    }

    private static boolean isNullMember(Member member) {
        return member == null;
    }
}
