package edu.java.repository.jpa;

import edu.java.model.Chat;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JpaChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Chat chat = Chat.builder()
            .id(10234L)
            .createdAt(OffsetDateTime.now())
            .build();
        assertDoesNotThrow(() -> jpaChatRepository.add(chat));
        Optional<Chat> actual = jpaChatRepository.findById(10234L);
        assertTrue(actual.isPresent());
        assertThat(actual.get().getId()).isEqualTo(10234L);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Chat chat = Chat.builder()
            .id(10235L)
            .createdAt(OffsetDateTime.now())
            .build();
        jpaChatRepository.add(chat);
        jpaChatRepository.remove(10235L);
        Optional<Chat> actual = jpaChatRepository.findById(10235L);
        assertTrue(actual.isEmpty());
    }
}
