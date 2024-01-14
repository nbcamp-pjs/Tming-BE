package com.spring.tming.domain.post.enums;

public enum Type {
    ALL("전체조회"),
    WRITE("작성한 모집글 전체조회"),
    APPLY("신청한 모집글 전체조회"),
    LIKE("좋아요한 모집글 전체조회"),
    MEMBER("신청승인된 모집글 전체조회"),
    SKILL("기술별 모집글 전체조회"),
    JOB("직군별 모집글 전체조회");

    private final String description;

    Type(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
