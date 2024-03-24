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
import org.springframework.transaction.annotation.Transactional;
import static edu.java.repository.jdbc.sql.SqlQueries.ADD_LINK_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.DELETE_LINK_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.FIND_LAST_VISITED_LINKS_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.FIND_LINK_BY_ID_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.FIND_LINK_BY_URL_QUERY;
import static edu.java.repository.jdbc.sql.SqlQueries.UPDATE_LINK_QUERY;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {
    private static final LinkMapper MAPPER = new LinkMapper();

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Link add(Link link) {
        return jdbcTemplate.queryForObject(
            ADD_LINK_QUERY, MAPPER,
            link.getUrl().toString()
        );
    }

    @Override
    @Transactional
    public Optional<Link> findByUrl(URI url) {
        return jdbcTemplate
            .queryForStream(FIND_LINK_BY_URL_QUERY, MAPPER, url.toString())
            .findAny();
    }

    @Override
    @Transactional
    public Optional<Link> findById(Long id) {
        return jdbcTemplate
            .queryForStream(FIND_LINK_BY_ID_QUERY, MAPPER, id)
            .findAny();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        jdbcTemplate.update(DELETE_LINK_QUERY, id);
    }

    @Override
    @Transactional
    public List<Link> findLastVisited(Integer size) {
        return jdbcTemplate.query(FIND_LAST_VISITED_LINKS_QUERY, MAPPER, size);
    }

    @Override
    @Transactional
    public void updateLink(Long id, OffsetDateTime updateAt, Integer updatesCount) {
        jdbcTemplate.update(
            UPDATE_LINK_QUERY,
            updateAt,
            updatesCount,
            id
        );
    }
}
