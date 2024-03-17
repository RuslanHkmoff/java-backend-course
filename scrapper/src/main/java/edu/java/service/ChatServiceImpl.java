package edu.java.service;

import edu.java.exception.ChatAlreadyExistsException;
import edu.java.exception.ChatDoesntExistsException;
import edu.java.model.Chat;
import edu.java.repository.ChatRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public void register(long chatId) {
        chatRepository.findById(chatId).ifPresent(chat -> {
            throw new ChatAlreadyExistsException("Chat is already exists");
        });
        chatRepository.add(Chat
            .builder()
            .id(chatId)
            .build());
    }

    @Override
    public void unregister(long chatId) {
        chatRepository.findById(chatId)
            .orElseThrow(() -> new ChatDoesntExistsException("Chat must be exists"));
        chatRepository.remove(chatId);
    }
}
