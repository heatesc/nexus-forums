package nexus.forums.db_interaction.repositories;

import nexus.forums.db_interaction.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
