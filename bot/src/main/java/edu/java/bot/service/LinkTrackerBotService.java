package edu.java.bot.service;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.model.command.Command;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LinkTrackerBotService {
    private final List<Command> commands;

    public LinkTrackerBotService(List<Command> commands) {
        this.commands = commands;
    }

    public SetMyCommands setCommandMenu() {
        BotCommand[] botCommands = commands.stream()
            .map(Command::toApiCommand)
            .toList()
            .toArray(new BotCommand[0]);
        return new SetMyCommands(botCommands);
    }

    public SendMessage handleUpdate(Update update) {
        Long id = update.message().chat().id();
        String commandFromUser = update.message().text().split("\\s+")[0];
        for (Command command : commands) {
            String currCommand = command.command();
            if (currCommand.equals(commandFromUser)) {
                return new SendMessage(id, command.handle(update));
            }
        }
        return new SendMessage(id, "Unknown command: '" + commandFromUser + "'");
    }
}
