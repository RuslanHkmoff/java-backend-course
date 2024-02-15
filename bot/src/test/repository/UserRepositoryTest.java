package repository;

import edu.java.bot.model.User;
import edu.java.bot.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryTest {
    private UserRepository userRepository;
    private final User user = new User(1L, new HashSet<>());

    @BeforeEach
    void init() {
        userRepository = new UserRepository();
        userRepository.save(user);
    }

    @Test
    @DisplayName("test find exist user")
    void testFindExistsUser() {
        Optional<User> got = userRepository.findById(1L);
        Assertions.assertTrue(got.isPresent());
        assertThat(got.get()).isEqualTo(user);
    }

    @Test
    @DisplayName("test find not exist user")
    void testFindNotExistsUser() {
        Optional<User> got = userRepository.findById(2L);
        Assertions.assertTrue(got.isEmpty());
    }
}
