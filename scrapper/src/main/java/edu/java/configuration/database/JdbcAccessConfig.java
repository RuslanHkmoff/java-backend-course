package edu.java.configuration.database;

import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.JdbcSubscriptionRepository;
import edu.java.service.ChatService;
import edu.java.service.ChatServiceImpl;
import edu.java.service.LinkService;
import edu.java.service.LinkServiceImpl;
import edu.java.service.client.BotService;
import edu.java.service.client.GithubService;
import edu.java.service.client.StackOverflowService;
import edu.java.service.updates.BotUpdateService;
import edu.java.service.updates.BotUpdateServiceImpl;
import edu.java.service.updates.LinkUpdateServiceImpl;
import edu.java.service.updates.LinkUpdatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "app.db-access-type", havingValue = "jdbc")
@Slf4j
public class JdbcAccessConfig {

    public static final String INFO_MESSAGE = "Using Jdbc";

    @Bean
    public LinkService linkService(
        JdbcLinkRepository jdbcLinkRepository,
        JdbcChatRepository jdbcChatRepository,
        JdbcSubscriptionRepository jdbcSubscriptionRepository
    ) {
        log.info(INFO_MESSAGE);
        return new LinkServiceImpl(
            jdbcLinkRepository,
            jdbcSubscriptionRepository,
            jdbcChatRepository
        );
    }

    @Bean
    public ChatService jdbcChatService(JdbcChatRepository jdbcChatRepository) {
        log.info(INFO_MESSAGE);
        return new ChatServiceImpl(jdbcChatRepository);
    }

    @Bean
    public LinkUpdatesService linkUpdatesService(
        JdbcLinkRepository jdbcLinkRepository,
        GithubService githubService,
        StackOverflowService stackOverflowService
    ) {
        log.info(INFO_MESSAGE);
        return new LinkUpdateServiceImpl(
            jdbcLinkRepository,
            githubService,
            stackOverflowService
        );
    }

    @Bean
    public BotUpdateService botUpdateService(
        BotService botService,
        JdbcLinkRepository jdbcLinkRepository,
        JdbcSubscriptionRepository jdbcSubscriptionsRepository
    ) {
        log.info(INFO_MESSAGE);
        return new BotUpdateServiceImpl(
            botService,
            jdbcLinkRepository,
            jdbcSubscriptionsRepository
        );
    }
}

