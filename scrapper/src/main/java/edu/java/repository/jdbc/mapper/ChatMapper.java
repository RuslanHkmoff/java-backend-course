package edu.java.repository.jdbc.mapper;

import edu.java.model.Chat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import org.springframework.jdbc.core.RowMapper;

public class ChatMapper implements RowMapper<Chat> {
    @Override
    public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Chat
            .builder()
            .id(rs.getLong("id"))
            .createdAt(rs.getObject("created_at", OffsetDateTime.class))
            .build();
    }
}
