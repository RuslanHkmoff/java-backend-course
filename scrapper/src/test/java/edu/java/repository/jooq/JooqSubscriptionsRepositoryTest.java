package edu.java.repository.jooq;

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
public class JooqSubscriptionsRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqSubscriptionsRepository jooqSubscriptionsRepository;

    @Autowired
    private JooqLinkRepository jooqLinkRepository;
    @Autowired
    private JooqChatRepository jooqChatRepository;

    @Test
    @Transactional
    @Rollback
    void addLinkToChatTest() {
        Link link = jooqLinkRepository.add(Link.builder().url(URI.create("test/uri1")).build());
        jooqChatRepository.add(Chat.builder().id(101L).build());
        assertDoesNotThrow(() -> jooqSubscriptionsRepository.addLinkToChat(101L, link.getId()));
    }

    @Test
    @Transactional
    @Rollback
    void findLinksByChatNotEmptyResultTest() {
        Link link = jooqLinkRepository.add(Link.builder().url(URI.create("test/uri2")).build());
        jooqChatRepository.add(Chat.builder().id(102L).build());
        jooqSubscriptionsRepository.addLinkToChat(102L, link.getId());
        List<Link> linksByChat = jooqSubscriptionsRepository.findLinksByChat(102L);
        Long actual = linksByChat.getFirst().getId();
        assertThat(link.getId()).isEqualTo(actual);
    }

    @Test
    @Transactional
    @Rollback
    void findLinksByChatEmptyResultTest() {
        jooqChatRepository.add(Chat.builder().id(103L).build());
        List<Link> linksByChat = jooqSubscriptionsRepository.findLinksByChat(103L);
        assertTrue(linksByChat.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void removeLinkFromChatTest() {
        Link link = jooqLinkRepository.add(Link.builder().url(URI.create("test/uri3")).build());
        jooqChatRepository.add(Chat.builder().id(104L).build());
        jooqSubscriptionsRepository.addLinkToChat(104L, link.getId());
        assertDoesNotThrow(() -> jooqSubscriptionsRepository.removeLinkFromChat(104L, link.getId()));
        List<Link> linksByChat = jooqSubscriptionsRepository.findLinksByChat(104L);
        assertTrue(linksByChat.isEmpty());
    }
}
