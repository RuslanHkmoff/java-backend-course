package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.models.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final Bot bot;

    public void sendAlert(LinkUpdateRequest linkUpdate) {
        linkUpdate.tgChatIds()
            .forEach(id ->
                bot.execute(new SendMessage(id, linkUpdate.description()))
            );
    }

}
