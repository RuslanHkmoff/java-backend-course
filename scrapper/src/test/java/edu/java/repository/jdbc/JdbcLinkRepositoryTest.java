package edu.java.repository.jdbc;

import edu.java.model.Link;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;

    @Test
    @Rollback
    void addTest() {
        Link link = new Link();
        URI url = URI.create("uri/test5");
        link.setUrl(url);
        Link add = jdbcLinkRepository.add(link);
        assertThat(add.getUrl()).isEqualTo(url);
    }

    @Test
    @Rollback
    void findByUrlTest() {
        URI url = URI.create("uri/test6");
        jdbcLinkRepository.add(Link.builder().url(url).build());
        assertThat(jdbcLinkRepository.findByUrl(url)).isNotEmpty();
    }

    @Test
    @Rollback
    void removeTest() {
        URI url = URI.create("uri/test7");
        jdbcLinkRepository.add(Link.builder().url(url).build());
        Long id = jdbcLinkRepository.findByUrl(url).get().getId();
        assertDoesNotThrow(() -> jdbcLinkRepository.remove(id));
        assertThat(jdbcLinkRepository.findById(id)).isEmpty();
    }

    @Test
    void updateLinkTest() {
        URI url = URI.create("uri/test8");
        jdbcLinkRepository.add(Link.builder().url(url).build());
        Long id = jdbcLinkRepository.findByUrl(url).get().getId();
        OffsetDateTime time = OffsetDateTime.parse(
            "2024-03-16 12:00:00 +00:00",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX")
        );
        jdbcLinkRepository.updateLink(id, time, 7);

        Optional<Link> actual = jdbcLinkRepository.findById(id);
        assertTrue(actual.isPresent());
        assertThat(actual.get().getUpdatedAt()).isEqualTo(time);
        assertThat(actual.get().getUpdatesCount()).isEqualTo(7);
    }
}
