package hw1.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.User;
import edu.java.bot.model.command.ListCommand;
import edu.java.bot.repository.UserRepository;
import java.net.URI;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListCommandTest {
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
    @DisplayName("test list command: command, description")
    void testBaseList() {
        ListCommand command = new ListCommand(userRepository);
        String name = "/list";
        String description = "Returns a list of tracked links";
        assertThat(command.command()).isEqualTo(name);
        assertThat(command.description()).isEqualTo(description);
    }

    @Test
    @DisplayName("test list: valid command")
    void testListValidCommand() {
        HashSet<URI> links = new HashSet<>();
        links.add(URI.create("Link1"));
        links.add(URI.create("Link2"));
        userRepository.save(new User(1L, links));
        ListCommand command = new ListCommand(userRepository);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/list");
        when(chat.id()).thenReturn(1L);

        String expected = """
            Tracking links:
            Link1
            Link2
            """.replace("\n", System.lineSeparator());
        assertThat(command.handle(update)).isEqualTo(expected);
    }

    @Test
    @DisplayName("test List: unregistered user")
    void testTrackUnregisteredUser() {
        ListCommand command = new ListCommand(userRepository);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/list");
        when(chat.id()).thenReturn(1L);

        String expected = "You need to be registered";
        assertThat(command.handle(update)).isEqualTo(expected);
    }
}
