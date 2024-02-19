package edu.java.bot.model.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final UserRepository userRepository;

    public ListCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        userRepository.findById(id).ifPresentOrElse(
            user -> {
                responseMessage.append("Tracking links:").append(LINE_SEPARATOR);
                user.getLinks().forEach(link -> responseMessage.append(link).append(LINE_SEPARATOR));
            },
            () -> responseMessage.append("You need to be registered")
        );
        return responseMessage.toString();
    }
}
