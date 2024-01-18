package com.spring.tming.domain.chat.entity;

import com.spring.tming.domain.model.BaseEntity;
import com.spring.tming.domain.user.entity.User;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@Entity
@Getter
@Table(name = "tb_chatRoom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Builder
    private ChatRoom(Long roomId, User sender, User receiver) {
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }
}
