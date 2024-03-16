package edu.java.repository.jdbc;

import edu.java.model.Link;
import edu.java.repository.LinkRepository;
import edu.java.repository.jdbc.mapper.LinkMapper;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {
    private static final String ADD_QUERY =
        "INSERT INTO link (id, url, visited_at) VALUES (default, ?, DEFAULT) RETURNING *";
    private static final String FIND_BY_ID_QUERY =
        "SELECT * FROM link WHERE id = ?";

    private static final String FIND_BY_URL_QUERY =
        "SELECT * FROM link WHERE url = ?";

    private static final String DELETE_QUERY =
        "DELETE FROM link WHERE id = ?";

    private static final String FIND_LAST_VISITED_QUERY =
        "SELECT id, url, visited_at, updated_at, updates_count"
            + "        FROM link"
            + "        ORDER BY visited_at NULLS FIRST"
            + "        LIMIT ?";

    private static final String UPDATE_QUERY =
        "UPDATE link SET updated_at = ?, updates_count = ? WHERE id = ?";
    private static final LinkMapper MAPPER = new LinkMapper();

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Link add(Link link) {
        return jdbcTemplate.queryForObject(
            ADD_QUERY, MAPPER,
            link.getUrl().toString()
        );
    }

    @Override
    public Optional<Link> findByUrl(URI url) {
        return jdbcTemplate
            .queryForStream(FIND_BY_URL_QUERY, MAPPER, url.toString())
            .findAny();
    }

    @Override
    public Optional<Link> findById(Long id) {
        return jdbcTemplate
            .queryForStream(FIND_BY_ID_QUERY, MAPPER, id)
            .findAny();
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public List<Link> findLastVisited(Integer size) {
        return jdbcTemplate.query(FIND_LAST_VISITED_QUERY, MAPPER, size);
    }

    @Override
    public void updateLink(Long id, OffsetDateTime updateAt, Integer updatesCount) {
        jdbcTemplate.update(
            UPDATE_QUERY,
            updateAt,
            updatesCount,
            id
        );
    }
}
