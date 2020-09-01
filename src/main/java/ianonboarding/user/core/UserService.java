package ianonboarding.user.core;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public User getUser(UUID id) {
		return userRepository.getOne(id);
	}
	
	public List<User> getUsersByLastName(String lastName){
		return userRepository.findUsersByLastNameIgnoreCase(lastName);
	}
	
	public Optional<User> findUser(UUID id) {
		return userRepository.findById(id);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

	public void delete(UUID id) {
		userRepository.deleteById(id);
	}
	
}
