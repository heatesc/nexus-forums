package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
