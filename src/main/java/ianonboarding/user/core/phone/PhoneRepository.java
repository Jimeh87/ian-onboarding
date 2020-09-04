package ianonboarding.user.core.phone;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, UUID> {
	
	public List<Phone> findPhonesByUserId(UUID userId);
	
	public boolean existsByPhoneId(UUID phoneId);
}
