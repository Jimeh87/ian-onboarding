package ianonboarding.user.core.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ianonboarding.user.core.phone.Phone;
import ianonboarding.user.core.phone.PhoneRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PhoneRepository phoneRepository;

	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public User getUser(UUID userId) {
		return userRepository.getOne(userId);
	}
	
	public List<User> getUsersByLastName(String lastName){
		return userRepository.findUsersByLastNameIgnoreCase(lastName);
	}
	
	public Optional<User> findUser(UUID userId) {
		return userRepository.findById(userId);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

	public void delete(UUID userId) {
		userRepository.deleteById(userId);
		List<Phone> phoneList = phoneRepository.findPhonesByUserId(userId);
		for(int i = 0; i < phoneList.size(); i++) {
			phoneRepository.deleteById(phoneList.get(i).getPhoneId());
		}
	}
	
	public boolean userNameExists(String username, UUID userId) {
		if(userId == null) {
			return userRepository.existsByUsername(username);
		}
		else {
			return userRepository.existsByUsernameAndUserIdNot(username, userId);
		}
	}
	
}
