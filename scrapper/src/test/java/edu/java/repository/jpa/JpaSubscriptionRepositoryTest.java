package edu.java.repository.jpa;

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
public class JpaSubscriptionRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaSubscriptionsRepository jpaSubscriptionsRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;
    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Test
    @Transactional
    @Rollback
    void addLinkToChatTest() {
        Link link = jpaLinkRepository.add(Link.builder().url(URI.create("test/uri1")).build());
        jpaChatRepository.add(Chat.builder().id(1010L).build());
        assertDoesNotThrow(() -> jpaSubscriptionsRepository.addLinkToChat(1010L, link.getId()));
    }

    @Test
    @Transactional
    @Rollback
    void findLinksByChatNotEmptyResultTest() {
        Link link = jpaLinkRepository.add(Link.builder().url(URI.create("test/uri2")).build());
        jpaChatRepository.add(Chat.builder().id(1020L).build());
        jpaSubscriptionsRepository.addLinkToChat(1020L, link.getId());
        List<Link> linksByChat = jpaSubscriptionsRepository.findLinksByChat(1020L);
        Long actual = linksByChat.getFirst().getId();
        assertThat(link.getId()).isEqualTo(actual);
    }

    @Test
    @Transactional
    @Rollback
    void findLinksByChatEmptyResultTest() {
        jpaChatRepository.add(Chat.builder().id(1030L).build());
        List<Link> linksByChat = jpaSubscriptionsRepository.findLinksByChat(1030L);
        assertTrue(linksByChat.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void removeLinkFromChatTest() {
        Link link = jpaLinkRepository.add(Link.builder().url(URI.create("test/uri3")).build());
        jpaChatRepository.add(Chat.builder().id(1040L).build());
        jpaSubscriptionsRepository.addLinkToChat(1040L, link.getId());
        assertDoesNotThrow(() -> jpaSubscriptionsRepository.removeLinkFromChat(1040L, link.getId()));
        List<Link> linksByChat = jpaSubscriptionsRepository.findLinksByChat(1040L);
        assertTrue(linksByChat.isEmpty());
    }
}
