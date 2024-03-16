package edu.java.service;

import edu.java.model.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Link save(long tgChatId, URI url);

    Link remove(long tgChatId, URI url);

    List<Link> getAll(long tgChatId);
}
