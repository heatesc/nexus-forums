package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
}
