package edu.service;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.command.Command;
import edu.java.bot.model.command.ListCommand;
import edu.java.bot.model.command.StartCommand;
import edu.java.bot.model.command.TrackCommand;
import edu.java.bot.model.command.UntrackCommand;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.service.LinkTrackerBotService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LinkTrackerBotServiceTest {
    private Update update;
    private Message message;
    private Chat chat;
    private LinkTrackerBotService service;
    private BotCommand[] botCommands;

    @BeforeEach
    void init() {
        update = mock(Update.class);
        message = mock(Message.class);
        chat = mock(Chat.class);
        UserRepository userRepository = new UserRepository();
        List<Command> commands = List.of(
            new StartCommand(userRepository),
            new TrackCommand(userRepository),
            new UntrackCommand(userRepository),
            new ListCommand(userRepository)
        );
        service = new LinkTrackerBotService(commands);
        botCommands = commands.stream().map(Command::toApiCommand).toList().toArray(new BotCommand[0]);

    }

    @Test
    void testHandleUpdate() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/help");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        assertThat(service.handleUpdate(update)).isNotNull();
    }

    @Test
    void testSetMenuCommand() {
        assertDoesNotThrow(() -> service.setCommandMenu());
    }
}
