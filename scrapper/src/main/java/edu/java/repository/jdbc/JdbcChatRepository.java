package edu.java.repository.jdbc;

import edu.java.model.Chat;
import edu.java.repository.ChatRepository;
import edu.java.repository.jdbc.mapper.ChatMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.repository.jdbc.sql.SqlQueries.ADD_CHAT_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.DELETE_CHAT_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.FIND_CHAT_BY_ID_QUERY;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository implements ChatRepository {
    private static final ChatMapper MAPPER = new ChatMapper();

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Chat add(Chat chat) {
        return jdbcTemplate.queryForObject(
            ADD_CHAT_QUERY, MAPPER,
            chat.getId()
        );
    }

    @Override
    @Transactional
    public Optional<Chat> findById(Long id) {
        return jdbcTemplate
            .queryForStream(FIND_CHAT_BY_ID_QUERY, MAPPER, id)
            .findAny();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        jdbcTemplate.update(DELETE_CHAT_QUERY, id);
    }
}
