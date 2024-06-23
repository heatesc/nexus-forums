package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Comment;
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
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;
    private Forum forum;
    private Post post;
    private Comment comment;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("commentuser");
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

        comment = new Comment();
        comment.setContent("Content of the test comment");
        comment.setCreatedAt(java.time.LocalDateTime.now());
        comment.setPost(post);
        comment.setCreatedBy(user);
        commentRepository.save(comment);
    }

    @Test
    public void testFindById() {
        entityManager.clear();
        Optional<Comment> foundComment = commentRepository.findById(comment.getId());
        assertTrue(foundComment.isPresent());
        assertEquals(comment.getContent(), foundComment.get().getContent());
    }

    @Test
    public void testSave() {
        entityManager.clear();
        Comment newComment = new Comment();
        newComment.setContent("Content of the new comment");
        newComment.setCreatedAt(java.time.LocalDateTime.now());
        newComment.setPost(post);
        newComment.setCreatedBy(user);
        Comment savedComment = commentRepository.save(newComment);
        assertNotNull(savedComment.getId());
    }

    @Test
    public void testDeleteById() {
        entityManager.clear();
        commentRepository.deleteById(comment.getId());
        Optional<Comment> deletedComment = commentRepository.findById(comment.getId());
        assertFalse(deletedComment.isPresent());
    }
}
