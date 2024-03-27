package edu.java.repository.jpa;

import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.model.Subscription;
import edu.java.repository.SubscriptionsRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.repository.jpa.hql.HqlQueries.DELETE_LINK_FROM_CHAT_QUERY;

@Repository
@RequiredArgsConstructor
public class JpaSubscriptionsRepository implements SubscriptionsRepository {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void addLinkToChat(Long chatId, Long linkId) {
        Chat chat = entityManager.find(Chat.class, chatId);
        Link link = entityManager.find(Link.class, linkId);

        Subscription subscription = Subscription.builder()
            .chatId(chatId)
            .linkId(linkId)
            .chat(chat)
            .link(link)
            .build();

        entityManager.persist(subscription);
    }

    @Transactional
    @Override
    public void removeLinkFromChat(Long chatId, Long linkId) {
        entityManager.createQuery(DELETE_LINK_FROM_CHAT_QUERY)
            .setParameter("chatId", chatId)
            .setParameter("linkId", linkId)
            .executeUpdate();
    }

    @Transactional
    @Override
    public List<Link> findLinksByChat(Long chatId) {
        return entityManager.find(Chat.class, chatId)
            .getSubscriptions()
            .stream()
            .map(Subscription::getLink)
            .toList();
    }

    @Transactional
    @Override
    public List<Chat> findChatsByLink(Long linkId) {
        return entityManager.find(Link.class, linkId)
            .getSubscriptions()
            .stream()
            .map(Subscription::getChat)
            .toList();
    }
}
