package ianonboarding.user.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ianonboarding.user.controller.UserDto;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserValidator {
	
	private UserRepository userRepository;

	public void validateAndThrow(UserDto userDto) {
		Map<String, String> errors = validate(userDto);

		if (!errors.isEmpty()) {
			throw new ValidationException(errors);
		}

	}

	public Map<String, String> validate(UserDto userDto) {
		Map<String, String> errors = new LinkedHashMap<>();
		if (StringUtils.isBlank(userDto.getUsername())) {
			errors.put("username", "REQUIRED");
		} else if (StringUtils.length(userDto.getUsername()) > 20) {
			errors.put("username", "INVALID_LENGTH");
		} else if (userRepository.existsByUsername(userDto.getUsername())) {
			errors.put("username", "NOT_UNIQUE");
		}

		if (StringUtils.isBlank(userDto.getFirstName())) {
			errors.put("firstName", "REQUIRED");
		}

		if (StringUtils.length(userDto.getFirstName()) > 50) {
			errors.put("firstName", "INVALID_LENGTH");
		}

		if (StringUtils.isBlank(userDto.getLastName())) {
			errors.put("lastName", "REQUIRED");
		}

		if (StringUtils.length(userDto.getLastName()) > 50) {
			errors.put("lastName", "INVALID_LENGTH");
		}

		return errors;
	}

}
