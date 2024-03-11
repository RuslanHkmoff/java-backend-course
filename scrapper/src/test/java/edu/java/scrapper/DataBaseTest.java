package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataBaseTest extends IntegrationTest {
    private static final String EXISTS_QUERY =
        "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = '%s')";
    private static final String INVALID_QUERY = "SELECT * FROM table_not_exists";

    @Test
    @DisplayName("test link table exists")
    void testLinkTableExists() {

        assertDoesNotThrow(() -> simpleTest(String.format(EXISTS_QUERY, "link")));
    }

    @Test
    @DisplayName("test chat table exists")
    void testChatTableExists() {
        assertDoesNotThrow(() -> simpleTest(String.format(EXISTS_QUERY, "chat")));
    }

    @Test
    @DisplayName("test subscriptions table exists")
    void testSubscriptionsTableExists() {
        assertDoesNotThrow(() -> simpleTest(String.format(EXISTS_QUERY, "subscriptions")));
    }

    @Test
    @DisplayName("test failed")
    void testFailed() {
        assertThrows(
            SQLException.class,
            () -> simpleTest(INVALID_QUERY)
        );
    }

    private void simpleTest(String sqlQuery) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
            POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),
            POSTGRES.getPassword()
        )) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                assertTrue(resultSet.next() && resultSet.getBoolean(1));
            }
        }
    }
}
