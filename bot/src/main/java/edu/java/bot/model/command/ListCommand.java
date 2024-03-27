package edu.java.bot.model.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.ScrapperService;
import edu.java.models.response.ListLinksResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final ScrapperService scrapperService;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Returns a list of tracked links";
    }

    @Override
    public String handle(Update update) {
        Long id = update.message().chat().id();
        StringBuilder responseMessage = new StringBuilder();
        Optional<ListLinksResponse> listLinksResponse = scrapperService.getAllLinks(id);
        listLinksResponse.ifPresentOrElse(
            allLinks -> {
                responseMessage.append("Tracking links:").append(LINE_SEPARATOR);
                allLinks.links().forEach(link -> responseMessage.append(link.url()).append(LINE_SEPARATOR));
            },
            () -> responseMessage.append("You need to be registered")
        );
        return responseMessage.toString();
    }
}
