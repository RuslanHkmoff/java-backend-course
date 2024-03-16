package edu.java.repository.jdbc;

import edu.java.model.Chat;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcChatRepositoryTest extends IntegrationTest {
    private static JdbcChatRepository jdbcChatRepository;

    @BeforeAll
    static void init() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(POSTGRES.getJdbcUrl());
        dataSource.setUsername(POSTGRES.getUsername());
        dataSource.setPassword(POSTGRES.getPassword());
        jdbcChatRepository = new JdbcChatRepository(new JdbcTemplate(dataSource));
    }

    @Test
    @Rollback
    void addTest() {
        Chat chat = new Chat(1234L, OffsetDateTime.now());
        assertDoesNotThrow(() -> jdbcChatRepository.add(chat));
        Optional<Chat> actual = jdbcChatRepository.findById(1234L);
        assertTrue(actual.isPresent());
        assertThat(actual.get().getId()).isEqualTo(1234L);
    }

    @Test
    @Rollback
    void removeTest() {
        Chat chat = new Chat(1235L, OffsetDateTime.now());
        jdbcChatRepository.add(chat);
        jdbcChatRepository.remove(1235L);
        Optional<Chat> actual = jdbcChatRepository.findById(1235L);
        assertTrue(actual.isEmpty());
    }
}
