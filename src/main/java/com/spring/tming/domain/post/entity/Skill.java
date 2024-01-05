package com.spring.tming.domain.post.entity;

public enum Skill {
    JAVA("Java"),
    PYTHON("Python"),
    JAVASCRIPT("JavaScript"),
    HTML_CSS("HTML/CSS"),
    SPRING("Spring Framework"),
    DJANGO("Django"),
    REACT("React"),
    ANGULAR("Angular"),
    SQL("SQL"),
    MONGODB("MongoDB"),
    GIT("Git"),
    DOCKER("Docker"),
    AWS("Amazon Web Services"),
    CI_CD("Continuous Integration/Continuous Deployment");

    private final String description;

    Skill(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
