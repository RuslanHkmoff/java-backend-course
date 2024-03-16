package edu.java.repository.jdbc;

import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcSubscriptionRepositoryTest extends IntegrationTest {
    private static final Long CHAT_ID = 12L;
    private static JdbcSubscriptionRepository jdbcSubscriptionRepository;
    private static Link link;

    @BeforeAll
    static void init() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(POSTGRES.getJdbcUrl());
        dataSource.setUsername(POSTGRES.getUsername());
        dataSource.setPassword(POSTGRES.getPassword());
        jdbcSubscriptionRepository = new JdbcSubscriptionRepository(new JdbcTemplate(dataSource));
        JdbcChatRepository jdbcChatRepository = new JdbcChatRepository(new JdbcTemplate(dataSource));
        JdbcLinkRepository jdbcLinkRepository = new JdbcLinkRepository(new JdbcTemplate(dataSource));
        jdbcChatRepository.add(Chat.builder().id(CHAT_ID).build());
        link = jdbcLinkRepository.add(Link.builder().url(URI.create("uri/test")).build());
    }

    @Test
    void addLinkToChatTest() {
        assertDoesNotThrow(() -> jdbcSubscriptionRepository.addLinkToChat(CHAT_ID, link.getId()));
    }

    @Test
    void findLinksByChatTest() {
        jdbcSubscriptionRepository.addLinkToChat(CHAT_ID, link.getId());
        List<Link> linksByChat = jdbcSubscriptionRepository.findLinksByChat(CHAT_ID);
        Long actual = linksByChat.getFirst().getId();
        assertThat(link.getId()).isEqualTo(actual);
    }

    @Test
    void removeLinkFromChatTest() {
        assertDoesNotThrow(() -> jdbcSubscriptionRepository.removeLinkFromChat(CHAT_ID, link.getId()));
        List<Link> linksByChat = jdbcSubscriptionRepository.findLinksByChat(CHAT_ID);
        assertTrue(linksByChat.isEmpty());
    }
}
