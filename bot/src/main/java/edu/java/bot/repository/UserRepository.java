package edu.java.bot.repository;

import edu.java.bot.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserRepository {
    private final Map<Long, User> map = new HashMap<>();

    public void save(User user) {
        map.put(user.getId(), user);
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }
}
