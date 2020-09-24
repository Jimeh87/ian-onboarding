package ianonboarding.user.controller.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ianonboarding.user.core.user.User;
import ianonboarding.user.core.user.UserService;
import ianonboarding.user.core.user.UserValidator;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private UserDtoAssembler userDtoAssembler;
	
	@GetMapping("{userId}")
	public UserDto getUser(@PathVariable("userId") UUID userId) {
		User user = userService.getUser(userId);
		return userDtoAssembler.assemble(user);
	}
	
	@GetMapping
	public List<UserDto> getUsers() {
		return userService.getUsers()
				.stream()
				.map(user -> userDtoAssembler.assemble(user))
				.collect(Collectors.toList());
	}
	
	@GetMapping(params = "lastName")
	public List<UserDto> getUsersByLastName(@RequestParam("lastName") String lastName){
		return userService.getUsersByLastName(lastName)
				.stream()
				.map(user -> userDtoAssembler.assemble(user))
				.collect(Collectors.toList());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createUser(@RequestBody UserDto userDto) {
		userValidator.validateAndThrow(userDto);
		User user = userDtoAssembler.disassemble(userDto);
		user = userService.save(user);
		return userDtoAssembler.assemble(user);
	}
	
	@PutMapping("{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@PathVariable("userId") UUID userId, @RequestBody UserDto userDto) {
		userDto.setUserId(userId);
		userValidator.validateAndThrow(userDto);
		User user = userDtoAssembler.disassemble(userDto);
		user = userService.save(user);
	}
	
	@DeleteMapping("{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("userId") UUID userId) {
		userService.delete(userId);
	}
	
}
