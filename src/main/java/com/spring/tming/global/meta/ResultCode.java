package com.spring.tming.global.meta;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(0, "정상 처리 되었습니다"),

    SYSTEM_ERROR(1000, "알 수 없는 에러가 발생했습니다."),
    NOT_FOUND_FILE(1001, "파일을 찾을 수 없습니다."),

    NOT_FOUND_SAMPLE(2002, "샘플 데이터가 없습니다."),

    // 이메일 유효성 검사
    INVALID_EMAIL(1002, "올바른 이메일 주소가 아닙니다. 다시 입력해주세요"),
    EMPTY_EMAIL(1003, "이메일을 입력해주세요"),
    DUPLICATED_USERNAME_OR_EMAIL(1004, "이메일 혹은 닉네임이 중복 되었습니다."),

    EMAIL_SEND_ERROR(2000, "이메일 전송 중 오류가 발생했습니다."),
    EMPTY_NUMBER(1004, "인증번호를 입력해주세요"),
    INVALID_NUMBER(1005, "유효하지 않은 인증번호입니다."),

    // 레디스 서버 연결 실패
    REDIS_CONNECTION_FAIL(3002, "레디스 서버와의 연결이 실패했습니다."),

    NOT_FOUND_USER(3000, "유저 정보가 없습니다."),
    PASSWORD_MISMATCH(3001, "비밀번호가 일치하지 않습니다."),
    VALID_USERNAME(3003, "username은 4자 이상, 12자 이하인 대소문자, 숫자, 한글로 구성되어야 합니다."),
    VALID_PASSWORD(3004, "password는 8자 이상, 16자 이하인 대소문자, 숫자, 특수문자를 모두 포함하여 구성되어야 합니다."),
    ALREADY_FOLLOWED_USER(3005, "이미 팔로우한 유저입니다."),
    NOT_YET_FOLLOWED_USER(3006, "아직 팔로우하지 않은 유저입니다."),

    NOT_FOUND_POST(4002, "모집글 데이터가 없습니다."),
    ALREADY_LIKED_POST(4003, "이미 좋아요가 눌린 모집글입니다."),
    NOT_YET_LIKED_POST(4004, "아직 좋아요가 눌리지 않은 모집글입니다."),
    POST_INVALID_AUTHORIZATION(4005, "해당 모집글에 권한이 없습니다."),
    POST_INVALID_FILTER(4006, "지원하지 않는 전체조회 필터입니다."),
    POST_INVALID_TITLE(4007, "title은 30자 안으로 작성되어야 합니다."),
    POST_INVALID_CONTENT(4008, "content는 1500자 안으로 작성되어야 합니다."),

    NOT_FOUND_COMMENT(5000, "댓글을 찾을 수 없거나 작성자가 아닙니다.");

    private Integer code;
    private String message;

    ResultCode(Integer code, String errorMessage) {
        this.code = code;
        this.message = errorMessage;
    }
}
