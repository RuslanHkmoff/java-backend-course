package edu.java.repository.jpa;

import edu.java.model.Link;
import edu.java.repository.LinkRepository;
import jakarta.persistence.EntityManager;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.repository.jpa.hql.HqlQueries.FIND_BY_URL_QUERY;
import static edu.java.repository.jpa.hql.HqlQueries.FIND_LAST_VISITED_LINKS_QUERY;
import static edu.java.repository.jpa.hql.HqlQueries.UPDATE_LINK_QUERY;

@Repository
@RequiredArgsConstructor
public class JpaLinkRepository implements LinkRepository {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public Link add(Link link) {
        link.setVisitedAt(OffsetDateTime.now());
        entityManager.persist(link);
        entityManager.flush();
        entityManager.refresh(link);
        return link;
    }

    @Override
    @Transactional
    public Optional<Link> findByUrl(URI url) {
        return entityManager.createQuery(FIND_BY_URL_QUERY, Link.class)
            .setParameter("url", url)
            .getResultList()
            .stream()
            .findFirst();
    }

    @Override
    @Transactional
    public Optional<Link> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Link.class, id));
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Optional<Link> byId = findById(id);
        byId.ifPresent(entityManager::remove);
    }

    @Override
    @Transactional
    public List<Link> findLastVisited(Integer size) {
        return entityManager.createQuery(FIND_LAST_VISITED_LINKS_QUERY, Link.class)
            .setParameter("limit", size)
            .getResultList();
    }

    @Override
    @Transactional
    public void updateLink(Long id, OffsetDateTime updatedAt, Integer updatesCount) {
        entityManager.createQuery(UPDATE_LINK_QUERY)
            .setParameter("updated_at", updatedAt)
            .setParameter("updates_count", updatesCount)
            .setParameter("id", id)
            .executeUpdate();
    }
}
