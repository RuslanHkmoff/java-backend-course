package edu.java.repository.jdbc.sql;

public final class SqlQueries {
    private SqlQueries() {
    }

    public static final String ADD_CHAT_QUERY =
        "INSERT INTO chat (id) VALUES (?) RETURNING *";

    public static final String FIND_CHAT_BY_ID_QUERY =
        "SELECT * FROM chat WHERE id = ?";
    public static final String DELETE_CHAT_QUERY =
        "DELETE FROM chat WHERE id = ?";
    public static final String ADD_LINK_QUERY =
        "INSERT INTO link (url, visited_at) VALUES (?, DEFAULT) RETURNING *";
    public static final String FIND_LINK_BY_ID_QUERY =
        "SELECT * FROM link WHERE id = ?";
    public static final String FIND_LINK_BY_URL_QUERY =
        "SELECT * FROM link WHERE url = ?";
    public static final String DELETE_LINK_QUERY =
        "DELETE FROM link WHERE id = ?";
    public static final String UPDATE_LINK_QUERY =
        "UPDATE link SET updated_at = ?, updates_count = ? WHERE id = ?";
    public static final String FIND_LAST_VISITED_LINKS_QUERY = """
        SELECT id, url, visited_at, updated_at, updates_count
        FROM link
        ORDER BY visited_at NULLS FIRST
        LIMIT ?
        """;
    public static final String ADD_LINK_TO_CHAT_QUERY =
        "INSERT INTO subscriptions (chat_id, link_id) VALUES (?, ?)";
    public static final String FIND_CHATS_BY_LINK_QUERY = """
        SELECT c.id, c.created_at FROM chat c
        JOIN subscriptions s ON c.id = s.link_id
        WHERE s.link_id = ?
        """;
    public static final String FIND_LINKS_BY_CHAT_QUERY = """
        SELECT l.id, l.url, l.visited_at, l.updated_at, l.updates_count FROM link l
        JOIN subscriptions s ON l.id = s.link_id
        WHERE  s.chat_id = ?
        """;

    public static final String DELETE_SUBSCRIPTION_QUERY =
        "DELETE FROM subscriptions WHERE chat_id = ? AND link_id = ?";
}
