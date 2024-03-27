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
import edu.java.bot.service.LinkTrackerBotService;
import java.util.List;
import edu.java.bot.service.ScrapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LinkTrackerBotServiceTest {
    private LinkTrackerBotService service;
    @Mock
    private ScrapperService scrapperService;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        List<Command> commands = List.of(
            new StartCommand(scrapperService),
            new TrackCommand(scrapperService),
            new UntrackCommand(scrapperService),
            new ListCommand(scrapperService)
        );
        service = new LinkTrackerBotService(commands);
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
