package edu.java.bot.model.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.ScrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnregisterCommand implements Command {
    private final ScrapperService scrapperService;

    @Override
    public String command() {
        return "/unregister";
    }

    @Override
    public String description() {
        return "Used for unregister user";
    }

    @Override
    public String handle(Update update) {
        Long id = update.message().chat().id();
        boolean isDelete = scrapperService.deleteChat(id);
        return isDelete ? "You have successfully unregistered" : "You cannot to be unregistered";
    }
}
