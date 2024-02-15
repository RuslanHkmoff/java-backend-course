package edu.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.User;
import edu.java.bot.model.command.TrackCommand;
import edu.java.bot.repository.UserRepository;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackCommandTest {
    private UserRepository userRepository;
    private Update update;
    private Message message;
    private Chat chat;
    private TrackCommand command;

    @BeforeEach
    void init() {
        userRepository = new UserRepository();
        update = mock(Update.class);
        message = mock(Message.class);
        chat = mock(Chat.class);
        command = new TrackCommand(userRepository);
    }

    @Test
    @DisplayName("test track command: command, description")
    void testBaseTrack() {
        String name = "/track";
        String description = "Used for start tracking link";
        assertThat(command.command()).isEqualTo(name);
        assertThat(command.description()).isEqualTo(description);
    }

    @Test
    @DisplayName("test track: valid command")
    void testTrackValidCommand() {
        userRepository.save(new User(1L, new HashSet<>()));
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/track link");
        when(chat.id()).thenReturn(1L);

        String expected = "Link successfully tracking";
        assertThat(command.handle(update)).isEqualTo(expected);
    }

    @Test
    @DisplayName("test track: invalid command")
    void testTrackInvalidCommand() {
        userRepository.save(new User(1L, new HashSet<>()));
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/track");
        when(chat.id()).thenReturn(1L);

        String expected = "A link must be provided";
        assertThat(command.handle(update)).isEqualTo(expected);
    }

    @Test
    @DisplayName("test track: unregistered user")
    void testTrackUnregisteredUser() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/track link");
        when(chat.id()).thenReturn(1L);

        String expected = "You need to be registered";
        assertThat(command.handle(update)).isEqualTo(expected);
    }

}
