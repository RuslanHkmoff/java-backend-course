package edu.java.service.updates;

import java.util.List;

public interface BotUpdateService {
    void sendUpdates(List<LinkUpdateDto> updatedLinks);
}
