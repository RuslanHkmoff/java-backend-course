package edu.java.service;

import edu.java.exception.ChatDoesntExistsException;
import edu.java.exception.LinkDoesntExistsException;
import edu.java.model.Link;
import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import edu.java.repository.SubscriptionRepository;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final ChatRepository chatRepository;

    @Override
    public Link save(long chatId, URI url) {
        isChatExistsOrElseThrow(chatId);
        Optional<Link> byUrl = linkRepository.findByUrl(url);
        Link link = byUrl.orElseGet(() -> linkRepository
            .add(Link.builder()
                .url(url)
                .build()
            )
        );
        subscriptionRepository.addLinkToChat(chatId, link.getId());
        return link;
    }

    @Override
    public Link remove(long chatId, URI url) {
        isChatExistsOrElseThrow(chatId);
        Link link = linkRepository
            .findByUrl(url)
            .orElseThrow(() -> new LinkDoesntExistsException("Link must be exists"));
        subscriptionRepository.removeLinkFromChat(chatId, link.getId());
        return link;
    }

    @Override
    public List<Link> getAll(long chatId) {
        isChatExistsOrElseThrow(chatId);
//        List<Long> linksId = subscriptionRepository.findLinksByChat(chatId);
//        return linksId.stream()
//            .map(id -> linkRepository.findById(id).orElseThrow())
//            .toList();
        return subscriptionRepository.findLinksByChat(chatId);
    }

    private void isChatExistsOrElseThrow(Long chatId) {
        chatRepository
            .findById(chatId)
            .orElseThrow(() -> new ChatDoesntExistsException("Chat must be exists"));
    }
}
