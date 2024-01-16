package com.spring.tming.global.meta;

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
