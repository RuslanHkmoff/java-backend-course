package edu;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.BotApplication;
import edu.java.bot.LinkTrackerBot;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static com.pengrad.telegrambot.UpdatesListener.CONFIRMED_UPDATES_ALL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BotApplication.class)
public class LinkTrackerBotTest {
    @Autowired
    private LinkTrackerBot bot;
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
    void testProcess() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/help");
        when(chat.id()).thenReturn(1L);
        List<Update> updates = List.of(update);

        assertThat(bot.process(updates)).isEqualTo(CONFIRMED_UPDATES_ALL);
    }

    @Test
    @DisplayName("test close")
    void testClose() {
        assertDoesNotThrow(() -> bot.close());
    }
}
