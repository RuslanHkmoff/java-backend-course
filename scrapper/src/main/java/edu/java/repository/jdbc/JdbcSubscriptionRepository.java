package edu.java.repository.jdbc;

import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.repository.SubscriptionsRepository;
import edu.java.repository.jdbc.mapper.ChatMapper;
import edu.java.repository.jdbc.mapper.LinkMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.repository.jdbc.sql.SqlQueries.ADD_LINK_TO_CHAT_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.DELETE_SUBSCRIPTION_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.FIND_CHATS_BY_LINK_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.FIND_LINKS_BY_CHAT_QUERY;

@RequiredArgsConstructor
@Repository
public class JdbcSubscriptionRepository implements SubscriptionsRepository {
    private static final LinkMapper LINK_MAPPER = new LinkMapper();
    private static final ChatMapper CHAT_MAPPER = new ChatMapper();

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void addLinkToChat(Long chatId, Long linkId) {
        jdbcTemplate.update(ADD_LINK_TO_CHAT_QUERY, chatId, linkId);
    }

    @Override
    @Transactional
    public void removeLinkFromChat(Long chatId, Long linkId) {
        jdbcTemplate.update(DELETE_SUBSCRIPTION_QUERY, chatId, linkId);
    }

    @Override
    @Transactional
    public List<Link> findLinksByChat(Long chatId) {
        return jdbcTemplate.query(FIND_LINKS_BY_CHAT_QUERY, LINK_MAPPER, chatId);
    }

    @Override
    @Transactional
    public List<Chat> findChatsByLink(Long linkId) {
        return jdbcTemplate.query(FIND_CHATS_BY_LINK_QUERY, CHAT_MAPPER, linkId);
    }
}
