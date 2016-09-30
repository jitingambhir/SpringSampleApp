package io.spring.start.repository;

import org.springframework.data.jpa.repository.*;

import io.spring.start.domain.Chat;

import java.util.List;

/**
 * Spring Data JPA repository for the Chat entity.
 */
@SuppressWarnings("unused")
public interface ChatRepository extends JpaRepository<Chat,Long> {

    @Query("select chat from Chat chat where chat.messageFrom.login = ?#{principal.username}")
    List<Chat> findByMessageFromIsCurrentUser();

    @Query("select chat from Chat chat where chat.messageTo.login = ?#{principal.username}")
    List<Chat> findByMessageToIsCurrentUser();

}
