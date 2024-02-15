package edu.java.bot.model.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.User;
import edu.java.bot.repository.UserRepository;
import java.util.LinkedHashSet;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private final UserRepository userRepository;

    public StartCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        User user = new User(id, new LinkedHashSet<>());
        userRepository.save(user);
        return "You have successfully registered!";
    }
}
