package ianonboarding.user.core.user;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	public List<User> findUsersByLastNameIgnoreCase(String lastName);
	
	public boolean existsByUsernameAndUserIdNot(String username, UUID userId);
	
	public boolean existsByUsername(String username);
	
}
