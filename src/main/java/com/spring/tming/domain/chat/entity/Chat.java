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
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom chatRoomId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column private String content;

    @Builder
    private Chat(Long chatId, ChatRoom chatRoom, User sender, String content) {
        this.chatId = chatId;
        this.chatRoomId = chatRoom;
        this.sender = sender;
        this.content = content;
    }
}
