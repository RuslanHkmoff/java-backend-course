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
import edu.java.bot.service.ScrapperService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class HelpCommandTest {
    @Mock
    private ScrapperService scrapperService;
    private List<Command> commands;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        commands = List.of(
            new StartCommand(scrapperService),
            new TrackCommand(scrapperService),
            new UntrackCommand(scrapperService),
            new ListCommand(scrapperService)
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
