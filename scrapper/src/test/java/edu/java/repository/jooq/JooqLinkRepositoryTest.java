package edu.java.repository.jooq;

import edu.java.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JooqLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JooqLinkRepository jooqLinkRepository;

    @Test
    @Rollback
    void addTest() {
        Link link = new Link();
        URI url = URI.create("uri/test");
        link.setUrl(url);
        Link add = jooqLinkRepository.add(link);
        assertThat(add.getUrl()).isEqualTo(url);
    }

    @Test
    @Rollback
    void findByUrlTest() {
        Link link = new Link();
        URI url = URI.create("uri/test");
        link.setUrl(url);
        jooqLinkRepository.add(link);
        assertThat(jooqLinkRepository.findByUrl(url)).isNotEmpty();
    }

    @Test
    @Rollback
    void removeTest() {
        URI url = URI.create("uri/test");
        Long id = jooqLinkRepository.findByUrl(url).get().getId();
        assertDoesNotThrow(() -> jooqLinkRepository.remove(id));
        assertThat(jooqLinkRepository.findById(id)).isEmpty();
    }

    @Test
    void updateLinkTest() {
        URI url = URI.create("uri/tesst");
        jooqLinkRepository.add(Link.builder().url(url).build());
        Long id = jooqLinkRepository.findByUrl(url).get().getId();
        OffsetDateTime time = OffsetDateTime.parse(
            "2024-03-16 12:00:00 +00:00",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX")
        );
        jooqLinkRepository.updateLink(id, time, 7);

        Optional<Link> actual = jooqLinkRepository.findById(id);
        assertTrue(actual.isPresent());
        assertThat(actual.get().getUpdatedAt()).isEqualTo(time);
    }
}
