package com.spring.tming.domain.chat.entity;

import com.spring.tming.domain.model.BaseEntity;
import com.spring.tming.domain.user.entity.User;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_chat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private ChatRoom chatRoomId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    @Column private String content;

    @Builder
    private Chat(Long chatId, ChatRoom chatRoom, User userId, String content) {
        this.chatId = chatId;
        this.chatRoomId = chatRoom;
        this.userId = userId;
        this.content = content;
    }
}
