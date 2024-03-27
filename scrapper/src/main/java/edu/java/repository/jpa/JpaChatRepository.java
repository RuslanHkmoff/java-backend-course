package edu.java.repository.jpa;

import edu.java.model.Chat;
import edu.java.repository.ChatRepository;
import jakarta.persistence.EntityManager;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JpaChatRepository implements ChatRepository {
    private final EntityManager entityManager;

    @Transactional
    @Override
    public Chat add(Chat chat) {
        chat.setCreatedAt(OffsetDateTime.now());
        entityManager.persist(chat);
        entityManager.flush();
        entityManager.refresh(chat);
        return chat;
    }

    @Transactional
    @Override
    public void remove(Long id) {
        Optional<Chat> byId = findById(id);
        byId.ifPresent(entityManager::remove);
    }

    @Transactional
    @Override
    public Optional<Chat> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Chat.class, id));
    }
}
