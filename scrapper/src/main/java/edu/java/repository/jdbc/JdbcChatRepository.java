package edu.java.repository.jdbc;

import edu.java.model.Chat;
import edu.java.repository.ChatRepository;
import edu.java.repository.jdbc.mapper.ChatMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository implements ChatRepository {
    private static final String ADD_QUERY =
        "INSERT INTO chat (id) VALUES (?) RETURNING *";
    private static final String FIND_BY_ID_QUERY =
        "SELECT * FROM chat WHERE id = ?";
    private static final String DELETE_QUERY =
        "DELETE FROM chat WHERE id = ?";
    private static final ChatMapper MAPPER = new ChatMapper();

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Chat add(Chat chat) {
        return jdbcTemplate.queryForObject(
            ADD_QUERY, MAPPER,
            chat.getId()
        );
    }

    @Override
    public Optional<Chat> findById(Long id) {
        return jdbcTemplate
            .queryForStream(FIND_BY_ID_QUERY, MAPPER, id)
            .findAny();
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }
}
