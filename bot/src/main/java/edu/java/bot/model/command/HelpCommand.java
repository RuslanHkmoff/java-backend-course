package edu.java.bot.model.command;

import com.pengrad.telegrambot.model.Update;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private static final String COMMAND_SEPARATOR = " - ";
    private final List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Returns a list of available commands";
    }

    @Override
    public String handle(Update update) {
        StringBuilder responseMessage = new StringBuilder();
        commands.forEach(command -> responseMessage
            .append(command.command())
            .append(COMMAND_SEPARATOR)
            .append(command.description())
            .append(LINE_SEPARATOR));
        return responseMessage.append(command())
            .append(COMMAND_SEPARATOR)
            .append(description())
            .append(LINE_SEPARATOR).toString();
    }
}
