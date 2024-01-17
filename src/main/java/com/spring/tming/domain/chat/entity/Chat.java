package com.spring.tming.domain.chat.entity;

import com.spring.tming.domain.user.entity.User;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Table(name = "tb_chat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Chat implements Serializable {
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

    @Column private LocalDateTime sendTime;

    @Builder
    private Chat(ChatRoom chatRoom, User sender, String content, LocalDateTime sendTime) {
        this.chatRoomId = chatRoom;
        this.sender = sender;
        this.content = content;
        this.sendTime = sendTime;
    }
}
