package com.spring.tming.domain.post.entity;

public enum Job {
    FRONTEND("Frontend Developer"),
    BACKEND("Backend Developer"),
    FULLSTACK("Full Stack Developer"),
    MOBILE("Mobile App Developer"),
    DEVOPS("DevOps Engineer"),
    QA("Quality Assurance Engineer"),
    DATA("Data Engineer"),
    AI_ML("AI/ML Engineer"),
    SECURITY("Security Engineer"),
    UI_UX("UI/UX Designer"),
    CLOUD("Cloud Engineer"),
    SOFTWARE_ARCHITECT("Software Architect"),
    TECH_LEAD("Tech Lead"),
    MANAGER("Development Manager");

    private final String description;

    Job(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
