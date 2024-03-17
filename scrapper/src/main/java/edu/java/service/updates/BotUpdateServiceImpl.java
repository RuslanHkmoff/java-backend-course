package edu.java.service.updates;

import edu.java.model.Chat;
import edu.java.models.request.LinkUpdateRequest;
import edu.java.repository.LinkRepository;
import edu.java.repository.SubscriptionRepository;
import edu.java.service.client.BotService;
import java.util.List;
import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class BotUpdateServiceImpl implements BotUpdateService {
    private final BotService botService;
    private final LinkRepository linkRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void sendUpdates(List<LinkUpdateDto> updatedLinks) {
        updatedLinks.forEach(dto -> {
            List<Long> chatIds = getChatIds(dto);
            botService.sendUpdate(new LinkUpdateRequest(1L, dto.url().toString(), dto.description(), chatIds));
        });
    }

    private List<Long> getChatIds(LinkUpdateDto update) {
        Long linkId = linkRepository.findByUrl(update.url()).orElseThrow().getId();
        return subscriptionRepository.findChatsByLink(linkId)
            .stream()
            .map(Chat::getId)
            .toList();

    }
}
