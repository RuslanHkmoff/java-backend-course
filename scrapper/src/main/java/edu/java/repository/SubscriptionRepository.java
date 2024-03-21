package edu.java.repository;

import edu.java.model.Chat;
import edu.java.model.Link;
import java.util.List;

public interface SubscriptionRepository {

    void addLinkToChat(Long chatId, Long linkId);

    void removeLinkFromChat(Long chatId, Long linkId);

    List<Link> findLinksByChat(Long chatId);

    List<Chat> findChatsByLink(Long linkId);

}
