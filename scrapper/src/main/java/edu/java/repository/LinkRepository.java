package edu.java.repository;

import edu.java.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {
    Link add(Link link);

    Optional<Link> findByUrl(URI url);

    Optional<Link> findById(Long id);

    void remove(Long id);

    List<Link> findLastVisited(Integer size);

    void updateLink(Long id, OffsetDateTime updateAt, Integer updatesCount);
}
