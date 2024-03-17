package edu.java.repository.jdbc;

import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcSubscriptionRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcSubscriptionRepository jdbcSubscriptionRepository;

    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;
    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    void addLinkToChatTest() {
        Link link = jdbcLinkRepository.add(Link.builder().url(URI.create("test/uri1")).build());
        jdbcChatRepository.add(Chat.builder().id(101L).build());
        assertDoesNotThrow(() -> jdbcSubscriptionRepository.addLinkToChat(101L, link.getId()));
    }

    @Test
    @Transactional
    @Rollback
    void findLinksByChatNotEmptyResultTest() {
        Link link = jdbcLinkRepository.add(Link.builder().url(URI.create("test/uri2")).build());
        jdbcChatRepository.add(Chat.builder().id(102L).build());
        jdbcSubscriptionRepository.addLinkToChat(102L, link.getId());
        List<Link> linksByChat = jdbcSubscriptionRepository.findLinksByChat(102L);
        Long actual = linksByChat.getFirst().getId();
        assertThat(link.getId()).isEqualTo(actual);
    }

    @Test
    @Transactional
    @Rollback
    void findLinksByChatEmptyResultTest() {
        jdbcChatRepository.add(Chat.builder().id(103L).build());
        List<Link> linksByChat = jdbcSubscriptionRepository.findLinksByChat(103L);
        assertTrue(linksByChat.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void removeLinkFromChatTest() {
        Link link = jdbcLinkRepository.add(Link.builder().url(URI.create("test/uri3")).build());
        jdbcChatRepository.add(Chat.builder().id(104L).build());
        jdbcSubscriptionRepository.addLinkToChat(104L, link.getId());
        assertDoesNotThrow(() -> jdbcSubscriptionRepository.removeLinkFromChat(104L, link.getId()));
        List<Link> linksByChat = jdbcSubscriptionRepository.findLinksByChat(104L);
        assertTrue(linksByChat.isEmpty());
    }
}
