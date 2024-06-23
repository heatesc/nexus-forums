package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
