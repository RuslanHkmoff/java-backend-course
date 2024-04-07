package edu.java.configuration.database;

import edu.java.repository.jooq.JooqChatRepository;
import edu.java.repository.jooq.JooqLinkRepository;
import edu.java.repository.jooq.JooqSubscriptionsRepository;
import edu.java.sender.UpdateSender;
import edu.java.service.ChatService;
import edu.java.service.ChatServiceImpl;
import edu.java.service.LinkService;
import edu.java.service.LinkServiceImpl;
import edu.java.service.client.GithubService;
import edu.java.service.client.StackOverflowService;
import edu.java.service.updates.BotUpdateService;
import edu.java.service.updates.BotUpdateServiceImpl;
import edu.java.service.updates.LinkUpdateServiceImpl;
import edu.java.service.updates.LinkUpdatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@ConditionalOnProperty(value = "app.db-access-type", havingValue = "jooq")
@ComponentScan("edu.java.repository.jooq")
public class JooqAccessConfig {

    private static final String INFO_MESSAGE = "Using jooq";

    @Bean
    public LinkService linkService(
        JooqLinkRepository jooqLinkRepository,
        JooqChatRepository jooqChatRepository,
        JooqSubscriptionsRepository jooqSubscriptionsRepository
    ) {
        log.info(INFO_MESSAGE);
        return new LinkServiceImpl(
            jooqLinkRepository,
            jooqSubscriptionsRepository,
            jooqChatRepository
        );
    }

    @Bean
    public ChatService jooqChatService(JooqChatRepository jooqChatRepository) {
        log.info(INFO_MESSAGE);
        return new ChatServiceImpl(jooqChatRepository);
    }

    @Bean
    public LinkUpdatesService linkUpdatesService(
        JooqLinkRepository jooqLinkRepository,
        GithubService githubService,
        StackOverflowService stackOverflowService
    ) {
        log.info(INFO_MESSAGE);
        return new LinkUpdateServiceImpl(
            jooqLinkRepository,
            githubService,
            stackOverflowService
        );
    }

    @Bean
    public BotUpdateService botUpdateService(
        UpdateSender updateSender,
        JooqLinkRepository jooqLinkRepository,
        JooqSubscriptionsRepository jooqSubscriptionsRepository
    ) {
        log.info(INFO_MESSAGE);
        return new BotUpdateServiceImpl(
            updateSender,
            jooqLinkRepository,
            jooqSubscriptionsRepository
        );
    }
}
