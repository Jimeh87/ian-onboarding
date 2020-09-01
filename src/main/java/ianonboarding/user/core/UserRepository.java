package ianonboarding.user.core;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	public List<User> findUserByLastNameIgnoreCase(String lastName);
	
}
