package edu.java.repository.jdbc;

import edu.java.model.Link;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationTest {
    private static JdbcLinkRepository jdbcLinkRepository;

    @BeforeAll
    static void init() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(POSTGRES.getJdbcUrl());
        dataSource.setUsername(POSTGRES.getUsername());
        dataSource.setPassword(POSTGRES.getPassword());
        jdbcLinkRepository = new JdbcLinkRepository(new JdbcTemplate(dataSource));
    }

    @Test
    @Rollback
    void addTest() {
        Link link = new Link();
        URI url = URI.create("uri/test");
        link.setUrl(url);
        Link add = jdbcLinkRepository.add(link);
        assertThat(add.getUrl()).isEqualTo(url);
    }

    @Test
    @Rollback
    void findByUrlTest() {
        Link link = new Link();
        URI url = URI.create("uri/test");
        link.setUrl(url);
        jdbcLinkRepository.add(link);
        assertThat(jdbcLinkRepository.findByUrl(url)).isNotEmpty();
    }

    @Test
    @Rollback
    void removeTest() {
        URI url = URI.create("uri/test");
        Long id = jdbcLinkRepository.findByUrl(url).get().getId();
        assertDoesNotThrow(() -> jdbcLinkRepository.remove(id));
        assertThat(jdbcLinkRepository.findById(id)).isEmpty();
    }

    @Test
    void updateLinkTest() {
        URI url = URI.create("uri/test");
        Long id = jdbcLinkRepository.findByUrl(url).get().getId();
        OffsetDateTime time = OffsetDateTime.parse(
            "2024-03-16 12:00:00 +00:00",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX")
        );
        jdbcLinkRepository.updateLink(id, time, 7);

        Optional<Link> actual = jdbcLinkRepository.findById(id);
        assertTrue(actual.isPresent());
        assertThat(actual.get().getUpdatedAt()).isEqualTo(time);
        assertThat(actual.get().getUpdateCount()).isEqualTo(7);
    }
}
