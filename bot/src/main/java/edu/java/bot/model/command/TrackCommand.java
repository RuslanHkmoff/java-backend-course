package edu.java.bot.model.command;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.User;
import edu.java.bot.repository.UserRepository;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final UserRepository userRepository;

    public TrackCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return "You need to be registered";
        }
        Set<URI> updateLinks = user.get().getLinks();
        updateLinks.add(URI.create(messageParts[1]));
        userRepository.save(new User(id, updateLinks));
        return "Link successfully tracking";
    }
}
