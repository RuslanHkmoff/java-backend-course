package edu.java.service.updates;

import edu.java.model.Chat;
import edu.java.models.request.LinkUpdateRequest;
import edu.java.repository.LinkRepository;
import edu.java.repository.SubscriptionsRepository;
import edu.java.sender.UpdateSender;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotUpdateServiceImpl implements BotUpdateService {
    private final UpdateSender updateSender;
    private final LinkRepository linkRepository;
    private final SubscriptionsRepository subscriptionRepository;

    @Override
    public void sendUpdates(List<LinkUpdateDto> updatedLinks) {
        updatedLinks.forEach(dto -> {
            List<Long> chatIds = getChatIds(dto);
            updateSender.sendUpdate(new LinkUpdateRequest(
                getLinkId(dto.url()),
                dto.url().toString(),
                dto.description(),
                chatIds
            ));
        });
    }

    private Long getLinkId(URI url) {
        return linkRepository.findByUrl(url).orElseThrow().getId();
    }

    private List<Long> getChatIds(LinkUpdateDto update) {
        Long linkId = linkRepository.findByUrl(update.url()).orElseThrow().getId();
        return subscriptionRepository.findChatsByLink(linkId)
            .stream()
            .map(Chat::getId)
            .toList();

    }
}
