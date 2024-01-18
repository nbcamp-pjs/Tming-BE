package com.spring.tming.domain.chat.entity;

import com.spring.tming.domain.model.BaseEntity;
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
    private Long chatRoomId;

    @Column private String chatRoomName;

    @Builder
    private ChatRoom(Long chatRoomId, String chatRoomName) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
    }
}
