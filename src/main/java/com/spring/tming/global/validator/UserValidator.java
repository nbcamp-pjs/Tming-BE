package com.spring.tming.global.validator;

import com.spring.tming.domain.sample.entity.Sample;
import com.spring.tming.global.exception.GlobalException;

import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_USER;

public class UserValidator {
    public static void validate(Sample sample) {
        if (isNullSample(sample)) {
            throw new GlobalException(NOT_FOUND_USER);
        }
    }

    private static boolean isNullSample(Sample sample) {
        return sample == null;
    }
}
