package com.spring.tming.domain.chat.repository;

import com.spring.tming.domain.chat.entity.ChatMember;
import com.spring.tming.domain.chat.entity.ChatRoom;
import com.spring.tming.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {
    List<Long> findUserIdUserIdByChatRoomIdChatRoomId(Long chatRoomId);

    List<ChatMember> findByUserId(User userId);

    ChatMember findByChatRoomIdAndUserIdNot(ChatRoom chatRoom, User userId);
}
