package edu.java.configuration.database;

import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.repository.jpa.JpaSubscriptionsRepository;
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
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "app.db-access-type", havingValue = "jpa")
@Slf4j
public class JpaAccessConfig {
    private static final String INFO_MESSAGE = "Using jpa";

    @Bean
    public LinkService linkService(
        JpaLinkRepository jpaLinkRepository,
        JpaChatRepository jpaChatRepository,
        JpaSubscriptionsRepository jpaSubscriptionsRepository
    ) {
        log.info(INFO_MESSAGE);
        return new LinkServiceImpl(
            jpaLinkRepository,
            jpaSubscriptionsRepository,
            jpaChatRepository
        );
    }

    @Bean
    public ChatService jpaChatService(JpaChatRepository jpaChatRepository) {
        log.info(INFO_MESSAGE);
        return new ChatServiceImpl(jpaChatRepository);
    }

    @Bean
    public LinkUpdatesService linkUpdatesService(
        JpaLinkRepository jpaLinkRepository,
        GithubService githubService,
        StackOverflowService stackOverflowService
    ) {
        log.info(INFO_MESSAGE);
        return new LinkUpdateServiceImpl(
            jpaLinkRepository,
            githubService,
            stackOverflowService
        );
    }

    @Bean
    public BotUpdateService botUpdateService(
        UpdateSender updateSender,
        JpaLinkRepository jpaLinkRepository,
        JpaSubscriptionsRepository jpaSubscriptionsRepository
    ) {
        log.info(INFO_MESSAGE);
        return new BotUpdateServiceImpl(
            updateSender,
            jpaLinkRepository,
            jpaSubscriptionsRepository
        );
    }
}
