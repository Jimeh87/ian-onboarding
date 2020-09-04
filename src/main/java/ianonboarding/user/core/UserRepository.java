package ianonboarding.user.core;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	public List<User> findUsersByLastNameIgnoreCase(String lastName);
	
	public boolean existsByUsernameAndIdNot(String username, UUID id);
	
	public boolean existsByUsername(String username);
	
}
