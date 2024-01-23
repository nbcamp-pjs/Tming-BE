package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_MEMBER;

import com.spring.tming.domain.members.entity.Member;
import com.spring.tming.global.exception.GlobalException;

public class MemberValidator {

    public static void validate(Member member) {
        if (isNullMember(member)) {
            throw new GlobalException(NOT_FOUND_MEMBER);
        }
    }

    private static boolean isNullMember(Member member) {
        return member == null;
    }
}
