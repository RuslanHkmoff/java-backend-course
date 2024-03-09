package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class DataBaseTest extends IntegrationTest {
    private static final String EXISTS_QUERY =
        "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = '%s')";

    @Test
    @DisplayName("test link table exists")
    void testLinkTableExists() {
        simpleTest(String.format(EXISTS_QUERY, "link"));
    }

    @Test
    @DisplayName("test chat table exists")
    void testChatTableExists() {
        simpleTest(String.format(EXISTS_QUERY, "chat"));
    }

    @Test
    @DisplayName("test subscriptions table exists")
    void testSubscriptionsTableExists() {
        simpleTest(String.format(EXISTS_QUERY, "subscriptions"));
    }

    private void simpleTest(String sqlQuery) {
        try (Connection connection = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),
            POSTGRES.getPassword()
        )) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                assertTrue(resultSet.next() && resultSet.getBoolean(1));
            }
        } catch (SQLException e) {
            log.error("test failed: {}", e.getMessage());
        }
    }
}
