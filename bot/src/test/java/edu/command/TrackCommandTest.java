package edu.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.model.command.TrackCommand;
import edu.java.bot.service.ScrapperService;
import edu.java.models.response.LinkResponse;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class TrackCommandTest {
    @Mock
    private ScrapperService scrapperService;
    private TrackCommand command;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        command = new TrackCommand(scrapperService);
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
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.text()).thenReturn("/track link");
        when(chat.id()).thenReturn(1L);
        when(scrapperService.addLink(1L, "link"))
            .thenReturn(Optional.of(new LinkResponse(1L, URI.create("link"))));

        String expected = "Link successfully tracking";
        assertThat(command.handle(update)).isEqualTo(expected);
    }

    @Test
    @DisplayName("test track: invalid command")
    void testTrackInvalidCommand() {
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
        when(scrapperService.addLink(1L, "link")).thenReturn(Optional.empty());
        String expected = "You cannot track command";
        assertThat(command.handle(update)).isEqualTo(expected);
    }

}
