package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Email;
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
public class EmailRepositoryTest {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;
    private Email email;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("emailuser");
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        email = new Email();
        email.setUser(user);
        email.setEmail("email@example.com");
        emailRepository.save(email);
    }

    @Test
    public void testFindById() {
        entityManager.clear();
        Optional<Email> foundEmail = emailRepository.findById(email.getEmailId());
        assertTrue(foundEmail.isPresent());
        assertEquals(email.getEmail(), foundEmail.get().getEmail());
    }

    @Test
    public void testSave() {
        entityManager.clear();
        User newUser = new User();
        newUser.setUsername("newemailuser");
        newUser.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(newUser);

        Email newEmail = new Email();
        newEmail.setUser(newUser);
        newEmail.setEmail("newemail@example.com");
        Email savedEmail = emailRepository.save(newEmail);
        assertNotNull(savedEmail.getEmailId());
    }

    @Test
    public void testDeleteById() {
        entityManager.clear();
        emailRepository.deleteById(email.getEmailId());
        Optional<Email> deletedEmail = emailRepository.findById(email.getEmailId());
        assertFalse(deletedEmail.isPresent());
    }
}
