package edu.java.bot.model.command;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.User;
import edu.java.bot.repository.UserRepository;
import java.net.URI;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    private final UserRepository userRepository;

    public UntrackCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Stop tracking the link";
    }

    @Override
    public String handle(Update update) {
        Message message = update.message();
        Long id = message.chat().id();
        String[] messageParts = message.text().split("\\s+");
        if (messageParts.length < 2) {
            return "A link must be provided";
        }
        StringBuilder responseMessage = new StringBuilder();
        userRepository.findById(id).ifPresentOrElse(
            user -> {
                Set<URI> updateLinks = user.getLinks();
                updateLinks.remove(URI.create(messageParts[1]));
                userRepository.save(new User(id, updateLinks));
                responseMessage.append("Link successfully untrack");
            },
            () -> responseMessage.append("You need to be registered")
        );
        return responseMessage.toString();
    }
}
