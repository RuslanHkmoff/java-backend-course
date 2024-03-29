package edu.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.command.StartCommand;
import edu.java.bot.service.ScrapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class StartCommandTest {
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
    }

    @Test
    @DisplayName("test start command")
    void testStart() {
        StartCommand command = new StartCommand(scrapperService);
        String name = "/start";
        String description = "Used to register user";
        assertThat(command.command()).isEqualTo(name);
        assertThat(command.description()).isEqualTo(description);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/start");
        when(chat.id()).thenReturn(1L);
        when(scrapperService.registerChat(1L)).thenReturn(true);
        String result = command.handle(update);

        String expected = "You have successfully registered!";
        assertThat(result).isEqualTo(expected);

    }
}
