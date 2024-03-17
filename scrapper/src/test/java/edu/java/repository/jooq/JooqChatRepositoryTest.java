package edu.java.repository.jooq;

import edu.java.model.Chat;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JooqChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Chat chat = new Chat(1234L, OffsetDateTime.now());
        assertDoesNotThrow(() -> jdbcChatRepository.add(chat));
        Optional<Chat> actual = jdbcChatRepository.findById(1234L);
        assertTrue(actual.isPresent());
        assertThat(actual.get().getId()).isEqualTo(1234L);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Chat chat = new Chat(1235L, OffsetDateTime.now());
        jdbcChatRepository.add(chat);
        jdbcChatRepository.remove(1235L);
        Optional<Chat> actual = jdbcChatRepository.findById(1235L);
        assertTrue(actual.isEmpty());
    }
}
