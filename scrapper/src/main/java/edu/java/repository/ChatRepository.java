package edu.java.repository;

import edu.java.model.Chat;
import java.util.Optional;

public interface ChatRepository {

    Chat add(Chat chat);

    void remove(Long id);

    Optional<Chat> findById(Long id);
}
