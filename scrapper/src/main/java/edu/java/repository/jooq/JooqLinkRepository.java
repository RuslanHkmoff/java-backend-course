package edu.java.repository.jooq;

import edu.java.model.Link;
import edu.java.repository.LinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.model.jooq.Tables.LINK;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {
    private final DSLContext dsl;

    @Override
    public Link add(Link link) {
        return dsl.insertInto(LINK, LINK.URL)
            .values(link.getUrl().toString())
            .returning()
            .fetchOneInto(Link.class);
    }

    @Override
    public Optional<Link> findByUrl(URI url) {
        return dsl.select(LINK.ID, LINK.URL, LINK.VISITED_AT, LINK.UPDATED_AT, LINK.UPDATES_COUNT)
            .from(LINK)
            .where(LINK.URL.eq(url.toString()))
            .fetchOptionalInto(Link.class);
    }

    @Override
    public Optional<Link> findById(Long id) {
        return dsl.select(LINK.ID, LINK.URL, LINK.VISITED_AT, LINK.UPDATED_AT, LINK.UPDATES_COUNT)
            .from(LINK)
            .where(LINK.ID.eq(id))
            .fetchOptionalInto(Link.class);
    }

    @Override
    public void remove(Long id) {
        dsl.deleteFrom(LINK)
            .where(LINK.ID.eq(id))
            .execute();
    }

    @Override
    public List<Link> findLastVisited(Integer size) {
        return dsl.select(LINK.ID, LINK.URL, LINK.VISITED_AT, LINK.UPDATED_AT, LINK.UPDATES_COUNT)
            .from(LINK)
            .orderBy(LINK.VISITED_AT.asc().nullsFirst())
            .limit(size)
            .fetchInto(Link.class);
    }

    @Override
    public void updateLink(Long id, OffsetDateTime updateAt, Integer updatesCount) {
        dsl.update(LINK)
            .set(LINK.UPDATED_AT, updateAt)
            .set(LINK.UPDATES_COUNT, updatesCount)
            .set(LINK.VISITED_AT, OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC))
            .where(LINK.ID.eq(id))
            .execute();
    }
}
