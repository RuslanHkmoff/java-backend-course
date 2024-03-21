package edu.java.repository.jooq;

import edu.java.model.Chat;
import edu.java.repository.ChatRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.model.jooq.Tables.CHAT;

@Repository
@RequiredArgsConstructor
public class JooqChatRepository implements ChatRepository {
    private final DSLContext dsl;

    @Override
    public Chat add(Chat chat) {
        return dsl.insertInto(CHAT)
            .values(chat.getId(), OffsetDateTime.now())
            .onConflictDoNothing()
            .returning()
            .fetchOneInto(Chat.class);
    }

    @Override
    public void remove(Long id) {
        dsl.deleteFrom(CHAT)
            .where(CHAT.ID.eq(id))
            .execute();
    }

    @Override
    public Optional<Chat> findById(Long id) {
        return dsl.select(CHAT.ID)
            .from(CHAT)
            .where(CHAT.ID.eq(id))
            .fetchOptionalInto(Chat.class);
    }
}
