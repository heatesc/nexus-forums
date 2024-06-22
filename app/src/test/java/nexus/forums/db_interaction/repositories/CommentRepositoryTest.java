package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Comment;
import nexus.forums.db_interaction.models.Post;
import nexus.forums.db_interaction.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Post post;
    private Comment comment;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("commentuser");
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        post = new Post();
        post.setTitle("Test Post");
        post.setContent("Content of the test post");
        post.setCreatedAt(java.time.LocalDateTime.now());
        post.setCreatedBy(user);
        postRepository.save(post);

        comment = new Comment();
        comment.setPost(post);
        comment.setContent("Content of the test comment");
        comment.setCreatedAt(java.time.LocalDateTime.now());
        comment.setCreatedBy(user);
        commentRepository.save(comment);
    }

    @Test
    public void testFindById() {
        Optional<Comment> foundComment = commentRepository.findById(comment.getId());
        assertTrue(foundComment.isPresent());
        assertEquals(comment.getContent(), foundComment.get().getContent());
    }

    @Test
    public void testSave() {
        Comment newComment = new Comment();
        newComment.setPost(post);
        newComment.setContent("New comment content");
        newComment.setCreatedAt(java.time.LocalDateTime.now());
        newComment.setCreatedBy(user);
        Comment savedComment = commentRepository.save(newComment);
        assertNotNull(savedComment.getId());
    }

    @Test
    public void testDeleteById() {
        commentRepository.deleteById(comment.getId());
        Optional<Comment> deletedComment = commentRepository.findById(comment.getId());
        assertFalse(deletedComment.isPresent());
    }
}
