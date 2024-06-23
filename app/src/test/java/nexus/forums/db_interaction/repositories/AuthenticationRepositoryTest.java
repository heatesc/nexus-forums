package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Authentication;
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
public class AuthenticationRepositoryTest {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;
    private Authentication authentication;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("authuser");
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        authentication = new Authentication();
        authentication.setUser(user);
        authentication.setPasswordHash("hashed_password");
        authenticationRepository.save(authentication);
    }

    @Test
    public void testFindById() {
        entityManager.clear();
        Optional<Authentication> foundAuth = authenticationRepository.findById(authentication.getAuthId());
        assertTrue(foundAuth.isPresent());
        assertEquals(authentication.getPasswordHash(), foundAuth.get().getPasswordHash());
    }

    @Test
    public void testSave() {
        entityManager.clear();
        User newUser = new User();
        newUser.setUsername("newauthuser");
        newUser.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(newUser);

        Authentication newAuth = new Authentication();
        newAuth.setUser(newUser);
        newAuth.setPasswordHash("new_hashed_password");
        Authentication savedAuth = authenticationRepository.save(newAuth);
        assertNotNull(savedAuth.getAuthId());
    }

    @Test
    public void testDeleteById() {
        entityManager.clear();
        authenticationRepository.deleteById(authentication.getAuthId());
        Optional<Authentication> deletedAuth = authenticationRepository.findById(authentication.getAuthId());
        assertFalse(deletedAuth.isPresent());
    }
}

