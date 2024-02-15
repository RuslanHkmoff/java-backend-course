package edu.java.bot;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.LinkTrackerBotService;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LinkTrackerBot implements Bot {
    private static final Logger LOGGER = LogManager.getLogger(LinkTrackerBot.class);
    private final TelegramBot bot;
    private final LinkTrackerBotService botService;

    public LinkTrackerBot(
        LinkTrackerBotService linkTrackerBotService,
        ApplicationConfig applicationConfig
    ) {
        this.botService = linkTrackerBotService;
        this.bot = new TelegramBot(applicationConfig.telegramToken());
        start();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null && update.message().text() != null) {
                bot.execute(botService.handleUpdate(update), new Callback<SendMessage, SendResponse>() {
                    @Override
                    public void onResponse(SendMessage request, SendResponse response) {
                        LOGGER.info("Sending response on request, message: "
                            + response.message().text());
                    }

                    @Override
                    public void onFailure(SendMessage request, IOException e) {
                        LOGGER.info("Exception while process request: " + e.getMessage());
                    }
                });
            }
        });
        return CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void start() {
        BaseResponse response = bot.execute(botService.setCommandMenu());
        if (response.isOk()) {
            LOGGER.info("Success set menu");
        } else {
            LOGGER.info("Error while set menu");
        }
        bot.setUpdatesListener(this);
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}
