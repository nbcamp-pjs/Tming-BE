package com.spring.tming.domain.emailVerify.dto.response;

import com.spring.tming.global.meta.ResultCode;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmailAuthRes<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> EmailAuthRes<T> success(T data) {
        return EmailAuthRes.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> EmailAuthRes<T> error(ResultCode resultCode) {
        return EmailAuthRes.<T>builder()
                .code(resultCode.getCode())
                .message(resultCode.getMessage())
                .build();
    }
}
