package com.spring.tming.domain.chat.entity;

import com.spring.tming.domain.user.entity.User;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@Entity
@Getter
@Table(name = "tb_chatMember")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoomId;

    @Builder
    private ChatMember(Long id, User userId, ChatRoom chatRoomId) {
        this.id = id;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
    }
}
