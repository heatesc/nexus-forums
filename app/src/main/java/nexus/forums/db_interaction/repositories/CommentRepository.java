package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
