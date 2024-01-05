package com.spring.tming.domain.post.entity;

import com.spring.tming.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column private String title;

    @Column private String content;

    @Column private Timestamp deadline;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column private Long visit;

    @Column private String imageUrl;

    //	@ManyToOne(fetch = FetchType.LAZY)
    //	@JoinColumn(name = "userId")
    //	private User user;
}
