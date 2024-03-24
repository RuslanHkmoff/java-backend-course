package edu.java.repository.jdbc.mapper;

import edu.java.model.Link;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import org.springframework.jdbc.core.RowMapper;

public class LinkMapper implements RowMapper<Link> {

    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Link
            .builder()
            .id(rs.getLong("id"))
            .url(URI.create(rs.getString("url")))
            .updatedAt(rs.getObject("updated_at", OffsetDateTime.class))
            .visitedAt(rs.getObject("visited_at", OffsetDateTime.class))
            .updatesCount(rs.getInt("updates_count"))
            .build();
    }
}
