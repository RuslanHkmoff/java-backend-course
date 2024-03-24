package edu.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.command.ListCommand;
import edu.java.bot.service.ScrapperService;
import edu.java.models.response.LinkResponse;
import edu.java.models.response.ListLinksResponse;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class ListCommandTest {
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
    @DisplayName("test list command: command, description")
    void testBaseList() {
        ListCommand command = new ListCommand(scrapperService);
        String name = "/list";
        String description = "Returns a list of tracked links";
        assertThat(command.command()).isEqualTo(name);
        assertThat(command.description()).isEqualTo(description);
    }

    @Test
    @DisplayName("test list: valid command")
    void testListValidCommand() {
        ListCommand command = new ListCommand(scrapperService);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/list");
        when(chat.id()).thenReturn(1L);
        when(scrapperService.getAllLinks(1L))
            .thenReturn(Optional.of(new ListLinksResponse(
                List.of(
                    new LinkResponse(2L, URI.create("Link1")),
                    new LinkResponse(3L, URI.create("Link2"))
                ),
                2
            )));

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
        ListCommand command = new ListCommand(scrapperService);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/list");
        when(chat.id()).thenReturn(1L);
        when(scrapperService.getAllLinks(1L)).thenReturn(Optional.empty());

        String expected = "You need to be registered";
        assertThat(command.handle(update)).isEqualTo(expected);
    }
}
