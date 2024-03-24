package edu.java.bot.model.command;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.ScrapperService;
import edu.java.models.response.LinkResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final ScrapperService scrapperService;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Used for start tracking link";
    }

    @Override
    public String handle(Update update) {
        Message message = update.message();
        Long id = message.chat().id();
        String[] messageParts = message.text().split("\\s+");
        if (messageParts.length < 2) {
            return "A link must be provided";
        }
        Optional<LinkResponse> linkResponse = scrapperService.addLink(id, messageParts[1]);
        return linkResponse.isPresent() ? "Link successfully tracking" : "You cannot track command";
    }
}
