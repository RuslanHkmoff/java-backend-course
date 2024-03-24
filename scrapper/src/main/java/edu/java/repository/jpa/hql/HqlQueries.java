package edu.java.repository.jpa.hql;

public final class HqlQueries {
    private HqlQueries() {
    }

    public static final String FIND_LAST_VISITED_LINKS_QUERY = """
                    FROM Link l
                    ORDER BY l.visitedAt NULLS FIRST
                    LIMIT :limit
        """;
    public static final String UPDATE_LINK_QUERY =
        "UPDATE Link l SET l.updatedAt = :updated_at, l.updatesCount = :updates_count WHERE l.id = :id";
    public static final String FIND_BY_URL_QUERY = "from Link l where l.url = :url";
    public static final String DELETE_LINK_FROM_CHAT_QUERY =
        "DELETE FROM Subscription s WHERE s.chat.id = :chatId AND s.link.id = :linkId";
}
