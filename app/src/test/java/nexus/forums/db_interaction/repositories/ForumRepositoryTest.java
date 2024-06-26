package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Forum;
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
public class ForumRepositoryTest {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;
    private Forum forum;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("forumadmin");
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        forum = new Forum();
        forum.setTitle("Test Forum");
        forum.setDescription("Description of the test forum");
        forum.setCreatedAt(java.time.LocalDateTime.now());
        forum.setAdmin(user);
        forumRepository.save(forum);
    }

    @Test
    public void testFindById() {
        entityManager.clear();
        Optional<Forum> foundForum = forumRepository.findById(forum.getId());
        assertTrue(foundForum.isPresent());
        assertEquals(forum.getTitle(), foundForum.get().getTitle());
    }

    @Test
    public void testSave() {
        entityManager.clear();
        Forum newForum = new Forum();
        newForum.setTitle("New Forum");
        newForum.setDescription("Description of the new forum");
        newForum.setCreatedAt(java.time.LocalDateTime.now());
        newForum.setAdmin(user);
        Forum savedForum = forumRepository.save(newForum);
        assertNotNull(savedForum.getId());
    }

    @Test
    public void testDeleteById() {
        entityManager.clear();
        forumRepository.deleteById(forum.getId());
        Optional<Forum> deletedForum = forumRepository.findById(forum.getId());
        assertFalse(deletedForum.isPresent());
    }
}
