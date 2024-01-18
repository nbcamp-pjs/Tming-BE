package com.spring.tming.domain.chat.entity;

import com.spring.tming.domain.user.entity.User;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@Entity
@Getter
@Table(name = "tb_chatMember")
@IdClass(ChatMemberId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMember implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User userId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "roomId")
    private ChatRoom chatRoomId;

    @Builder
    private ChatMember(User userId, ChatRoom chatRoomId) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
    }
}
