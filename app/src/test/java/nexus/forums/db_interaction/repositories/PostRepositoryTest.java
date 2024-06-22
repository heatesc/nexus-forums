package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Post;
import nexus.forums.db_interaction.models.User;
import nexus.forums.db_interaction.models.Forum;
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
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForumRepository forumRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;
    private Forum forum;
    private Post post;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("postuser");
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        forum = new Forum();
        forum.setTitle("Test Forum");
        forum.setDescription("Description of the test forum");
        forum.setCreatedAt(java.time.LocalDateTime.now());
        forum.setAdmin(user);
        forumRepository.save(forum);

        post = new Post();
        post.setTitle("Test Post");
        post.setContent("Content of the test post");
        post.setCreatedAt(java.time.LocalDateTime.now());
        post.setForum(forum);
        post.setCreatedBy(user);
        postRepository.save(post);
    }

    @Test
    public void testFindById() {
        entityManager.clear();
        Optional<Post> foundPost = postRepository.findById(post.getId());
        assertTrue(foundPost.isPresent());
        assertEquals(post.getTitle(), foundPost.get().getTitle());
    }

    @Test
    public void testSave() {
        entityManager.clear();
        Post newPost = new Post();
        newPost.setTitle("New Post");
        newPost.setContent("Content of the new post");
        newPost.setCreatedAt(java.time.LocalDateTime.now());
        newPost.setForum(forum);
        newPost.setCreatedBy(user);
        Post savedPost = postRepository.save(newPost);
        assertNotNull(savedPost.getId());
        assertEquals(forum.getId(), savedPost.getForum().getId());
    }

    @Test
    public void testDeleteById() {
        entityManager.clear();
        postRepository.deleteById(post.getId());
        Optional<Post> deletedPost = postRepository.findById(post.getId());
        assertFalse(deletedPost.isPresent());
    }
}
