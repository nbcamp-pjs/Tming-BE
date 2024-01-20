package com.spring.tming.domain.chat.repository;

import com.spring.tming.domain.chat.entity.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByChatRoomIdChatRoomId(Long chatRoomId);
}
