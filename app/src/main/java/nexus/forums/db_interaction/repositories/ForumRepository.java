package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
