package edu.java.bot.model.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.ScrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperService scrapperService;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Used to register user";
    }

    @Override
    public String handle(Update update) {
        Long id = update.message().chat().id();
        boolean isRegistered = scrapperService.registerChat(id);
        return isRegistered ? "You have successfully registered!" : "You cannot to be registered";
    }
}

