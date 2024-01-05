package com.spring.tming.global.validator;

import static com.spring.tming.global.meta.ResultCode.NOT_FOUND_SAMPLE;

import com.spring.tming.domain.sample.entity.Sample;
import com.spring.tming.global.exception.GlobalException;

public class SampleValidator {
    public static void validate(Sample sample) {
        if (isNullSample(sample)) {
            throw new GlobalException(NOT_FOUND_SAMPLE);
        }
    }

    private static boolean isNullSample(Sample sample) {
        return sample == null;
    }
}
