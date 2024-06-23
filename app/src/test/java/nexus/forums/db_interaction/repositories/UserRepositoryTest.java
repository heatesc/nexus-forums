package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
    }

    @Test
    public void testFindById() {
        entityManager.clear();
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    @Test
    public void testSave() {
        entityManager.clear();
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setCreatedAt(java.time.LocalDateTime.now());
        User savedUser = userRepository.save(newUser);
        assertNotNull(savedUser.getId());
    }

    @Test
    public void testDeleteById() {
        entityManager.clear();
        userRepository.deleteById(user.getId());
        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertFalse(deletedUser.isPresent());
    }
}
