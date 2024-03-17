package edu.java.repository.jdbc;

import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.repository.SubscriptionRepository;
import edu.java.repository.jdbc.mapper.ChatMapper;
import edu.java.repository.jdbc.mapper.LinkMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JdbcSubscriptionRepository implements SubscriptionRepository {
    private static final String ADD_LINK_QUERY =
        "INSERT INTO subscriptions (chat_id, link_id) VALUES (?, ?)";
    private static final String FIND_CHATS_QUERY =
        "SELECT c.id, c.created_at FROM chat c"
            + " JOIN subscriptions s ON c.id = s.link_id"
            + " WHERE s.link_id = ?";

    private static final String FIND_LINKS_QUERY =
        "SELECT l.id, l.url, l.visited_at, l.updated_at, l.updates_count FROM link l"
            + " JOIN subscriptions s ON l.id = s.link_id"
            + " WHERE  s.chat_id = ?";
    private static final String DELETE_QUERY =
        "DELETE FROM subscriptions WHERE chat_id = ? AND link_id = ?";
    private static final LinkMapper LINK_MAPPER = new LinkMapper();
    private static final ChatMapper CHAT_MAPPER = new ChatMapper();

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLinkToChat(Long chatId, Long linkId) {
        jdbcTemplate.update(ADD_LINK_QUERY, chatId, linkId);
    }

    @Override
    public void removeLinkFromChat(Long chatId, Long linkId) {
        jdbcTemplate.update(DELETE_QUERY, chatId, linkId);
    }

    @Override
    public List<Link> findLinksByChat(Long chatId) {
        return jdbcTemplate.query(FIND_LINKS_QUERY, LINK_MAPPER, chatId);
    }

    @Override
    public List<Chat> findChatsByLink(Long linkId) {
        return jdbcTemplate.query(FIND_CHATS_QUERY, CHAT_MAPPER, linkId);
    }
}
