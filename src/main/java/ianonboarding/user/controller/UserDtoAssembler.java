package ianonboarding.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ianonboarding.user.core.User;
import ianonboarding.user.core.UserService;

@Component
public class UserDtoAssembler {
	
	@Autowired
	private UserService userService;
	
	
	public UserDto assemble(User user) {
		return new UserDto()
				.setId(user.getId())
				.setUsername(user.getUsername())
				.setFirstName(user.getFirstName())
				.setLastName(user.getLastName());
	}
	
	public User disassemble(UserDto userDto) {
		User user = userDto.getId() != null
				? userService.getUser(userDto.getId())
				: User.newInstance(userDto.getUsername());
		
		return user
				.setFirstName(userDto.getFirstName())
				.setLastName(userDto.getLastName());
	}

}
