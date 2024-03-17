package edu.java.repository.jooq;

import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.repository.SubscriptionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.model.jooq.Tables.CHAT;
import static edu.java.model.jooq.Tables.SUBSCRIPTIONS;
import static edu.java.model.jooq.tables.Link.LINK;

@Repository
@RequiredArgsConstructor
public class JooqSubscriptionsRepository implements SubscriptionRepository {
    private final DSLContext dsl;

    @Override
    public void addLinkToChat(Long chatId, Long linkId) {
        dsl.insertInto(SUBSCRIPTIONS, SUBSCRIPTIONS.CHAT_ID, SUBSCRIPTIONS.LINK_ID)
            .values(chatId, linkId)
            .execute();
    }

    @Override
    public void removeLinkFromChat(Long chatId, Long linkId) {
        dsl.delete(SUBSCRIPTIONS)
            .where(SUBSCRIPTIONS.CHAT_ID.eq(chatId).and(SUBSCRIPTIONS.LINK_ID.eq(linkId)))
            .execute();
    }

    @Override
    public List<Link> findLinksByChat(Long chatId) {
        return dsl.select(LINK.ID, LINK.URL, LINK.UPDATED_AT, LINK.VISITED_AT, LINK.UPDATES_COUNT)
            .from(LINK)
            .join(SUBSCRIPTIONS)
            .on(LINK.ID.eq(SUBSCRIPTIONS.LINK_ID))
            .where(SUBSCRIPTIONS.CHAT_ID.eq(chatId))
            .fetchInto(Link.class);
    }

    @Override
    public List<Chat> findChatsByLink(Long linkId) {
        return dsl.select(CHAT.ID)
            .from(CHAT)
            .join(SUBSCRIPTIONS)
            .on(CHAT.ID.eq(SUBSCRIPTIONS.CHAT_ID))
            .where(SUBSCRIPTIONS.LINK_ID.eq(linkId))
            .fetchInto(Chat.class);
    }
}
