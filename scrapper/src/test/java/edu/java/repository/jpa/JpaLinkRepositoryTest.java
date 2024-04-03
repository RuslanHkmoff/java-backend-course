package edu.java.repository.jpa;

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
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JpaLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Link link = new Link();
        URI url = URI.create("uri/test10");
        link.setUrl(url);
        Link add = jpaLinkRepository.add(link);
        assertThat(add.getUrl()).isEqualTo(url);
    }

    @Test
    @Transactional
    @Rollback
    void findByUrlTest() {
        URI url = URI.create("uri/test11");
        jpaLinkRepository.add(Link.builder().url(url).build());
        assertThat(jpaLinkRepository.findByUrl(url)).isNotEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        URI url = URI.create("uri/test12");
        jpaLinkRepository.add(Link.builder().url(url).build());
        Long id = jpaLinkRepository.findByUrl(url).get().getId();
        assertDoesNotThrow(() -> jpaLinkRepository.remove(id));
        assertThat(jpaLinkRepository.findById(id)).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void updateLinkTest() {
        URI url = URI.create("uri/test13");
        jpaLinkRepository.add(Link.builder().url(url).build());
        Long id = jpaLinkRepository.findByUrl(url).get().getId();
        OffsetDateTime time = OffsetDateTime.parse(
            "2024-03-16 12:00:00 +00:00",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX")
        );
        jpaLinkRepository.updateLink(id, time, 7);

        Optional<Link> actual = jpaLinkRepository.findById(id);
        assertTrue(actual.isPresent());
    }
}
