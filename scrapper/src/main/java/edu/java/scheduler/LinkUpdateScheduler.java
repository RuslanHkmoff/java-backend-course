package edu.java.scheduler;

import edu.java.service.updates.BotUpdateService;
import edu.java.service.updates.LinkUpdateDto;
import edu.java.service.updates.LinkUpdatesService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdateScheduler {
    private static final int BATCH_SIZE = 10;
    private final LinkUpdatesService linkUpdatesService;
    private final BotUpdateService botUpdateService;

    @Scheduled(fixedDelayString = "#{@scheduler.interval().toMillis()}")
    public void update() {
        List<LinkUpdateDto> updatedLinks = linkUpdatesService.getUpdatedLink(BATCH_SIZE);
        if (!updatedLinks.isEmpty()) {
            log.info("[Scheduler]: send updates");
            botUpdateService.sendUpdates(updatedLinks);
        }
    }
}
