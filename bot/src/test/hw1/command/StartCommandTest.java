package hw1.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.command.StartCommand;
import edu.java.bot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartCommandTest {
    private UserRepository userRepository;
    private Update update;
    private Message message;
    private Chat chat;

    @BeforeEach
    void init() {
        userRepository = new UserRepository();
        update = mock(Update.class);
        message = mock(Message.class);
        chat = mock(Chat.class);
    }

    @Test
    @DisplayName("test start command")
    void testStart() {
        StartCommand command = new StartCommand(userRepository);
        String name = "/start";
        String description = "Used to register user";
        assertThat(command.command()).isEqualTo(name);
        assertThat(command.description()).isEqualTo(description);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/start");
        when(chat.id()).thenReturn(1L);

        String result = command.handle(update);

        String expected = "You have successfully registered!";
        assertThat(result).isEqualTo(expected);

    }
}
