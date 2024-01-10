package com.spring.tming.domain.post.entity;

public enum Status {
    OPEN("모집중"),
    CLOSED("모집완료");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
