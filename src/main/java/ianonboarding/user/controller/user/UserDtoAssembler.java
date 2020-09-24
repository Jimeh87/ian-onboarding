package ianonboarding.user.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ianonboarding.user.core.user.User;
import ianonboarding.user.core.user.UserService;

@Component
public class UserDtoAssembler {
	
	@Autowired
	private UserService userService;
	
	
	public UserDto assemble(User user) {
		return new UserDto()
				.setUserId(user.getUserId())
				.setUsername(user.getUsername())
				.setFirstName(user.getFirstName())
				.setLastName(user.getLastName());
	}
	
	public User disassemble(UserDto userDto) {
		User user = userDto.getUserId() != null
				? userService.getUser(userDto.getUserId())
				: User.newInstance(userDto.getUsername());
		
		return user
				.setFirstName(userDto.getFirstName())
				.setLastName(userDto.getLastName());
	}

}
