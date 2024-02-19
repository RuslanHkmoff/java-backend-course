package edu.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.User;
import edu.java.bot.model.command.Command;
import edu.java.bot.model.command.UntrackCommand;
import edu.java.bot.repository.UserRepository;
import java.net.URI;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UntrackCommandTest {
    private UserRepository userRepository;
    private Command command;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        userRepository = new UserRepository();
        command = new UntrackCommand(userRepository);
    }

    @Test
    @DisplayName("test untrack command: command, description")
    void testBaseUntrack() {
        String name = "/untrack";
        String description = "Stop tracking the link";
        assertThat(command.command()).isEqualTo(name);
        assertThat(command.description()).isEqualTo(description);
    }

    @Test
    @DisplayName("test untrack: valid command")
    void testUntrackValidCommand() {
        HashSet<URI> links = new HashSet<>();
        links.add(URI.create("link"));
        userRepository.save(new User(1L, links));
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/untrack link");
        when(chat.id()).thenReturn(1L);

        String expected = "Link successfully untrack";
        assertThat(command.handle(update)).isEqualTo(expected);
    }

    @Test
    @DisplayName("test untrack: invalid command")
    void testUntrackInvalidCommand() {
        userRepository.save(new User(1L, new HashSet<>()));
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/untrack");
        when(chat.id()).thenReturn(1L);

        String expected = "A link must be provided";
        assertThat(command.handle(update)).isEqualTo(expected);
    }

    @Test
    @DisplayName("test untrack: unregistered user")
    void testTrackUnregisteredUser() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/untrack link");
        when(chat.id()).thenReturn(1L);

        String expected = "You need to be registered";
        assertThat(command.handle(update)).isEqualTo(expected);
    }
}
