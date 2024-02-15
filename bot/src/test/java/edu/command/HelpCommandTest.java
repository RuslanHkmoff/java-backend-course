package edu.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.command.Command;
import edu.java.bot.model.command.HelpCommand;
import edu.java.bot.model.command.ListCommand;
import edu.java.bot.model.command.StartCommand;
import edu.java.bot.model.command.TrackCommand;
import edu.java.bot.model.command.UntrackCommand;
import edu.java.bot.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {
    private List<Command> commands;
    private Update update;
    private Message message;
    private Chat chat;

    @BeforeEach
    void init() {
        update = mock(Update.class);
        message = mock(Message.class);
        chat = mock(Chat.class);
        UserRepository userRepository = new UserRepository();
        commands = List.of(
            new StartCommand(userRepository),
            new TrackCommand(userRepository),
            new UntrackCommand(userRepository),
            new ListCommand(userRepository)
        );
    }

    @Test
    @DisplayName("test help command")
    void testHelpCommand() {
        Command help = new HelpCommand(commands);

        when(update.message()).thenReturn(message);
        when(chat.id()).thenReturn(1L);
        when(message.text()).thenReturn("/help");
        when(message.chat()).thenReturn(chat);

        String expected = """
            /start - Used to register user
            /track - Used for start tracking link
            /untrack - Stop tracking the link
            /list - Returns a list of tracked links
            /help - Returns a list of available commands
            """.replace("\n", System.lineSeparator());
        assertThat(help.handle(update)).isEqualTo(expected);
    }
}
