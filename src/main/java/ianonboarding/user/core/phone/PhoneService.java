package ianonboarding.user.core.phone;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
	
	@Autowired
	private PhoneRepository phoneRepository;
	
	public List<Phone> getPhones(){
		return phoneRepository.findAll();
	}

	public Phone getPhone(UUID id) {
		return phoneRepository.getOne(id);
	}
	
	public Optional<Phone> findPhone(UUID id){
		return phoneRepository.findById(id);
	}
	
	public Phone save(Phone phone) {
		// sent phone verification via twillio
		// if no phones are on user make this phone primary
		return phoneRepository.save(phone);
	}
	
	public void verify(UUID id, String verificationCode) {
		// twillioVerification.verificationCheck();
	}
	
	public void delete (UUID id) {
		phoneRepository.deleteById(id);
	}

	public List<Phone> getPhonesByUserId(UUID userId) {
		return phoneRepository.findPhonesByUserId(userId);
	}
}
