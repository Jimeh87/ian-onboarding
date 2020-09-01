package ianonboarding.user.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ianonboarding.user.controller.UserDto;

public class UserValidatorTest {

	private UserRepository userRepository = mock(UserRepository.class);
	private UserValidator userValidator = new UserValidator(userRepository);

	@Test
	public void testValidate_NoUsername_ExpectUsernameRequired() {
		when(userRepository.existsByUsername(any())).thenReturn(false);
		UserDto userDto = new UserDto()
				.setUsername(null)
				.setFirstName("Will")
				.setLastName("Smith");

		Map<String, String> errorTesting = userValidator.validate(userDto);
		assertEquals("REQUIRED", errorTesting.get("username"));

	}

	@Test
	public void testValidate_FirstNameLengthTooLong_ExpectInvalidLength() {
		when(userRepository.existsByUsername(any())).thenReturn(false);
		UserDto user = new UserDto()
				.setUsername("BadActor")
				.setFirstName("Jaden".repeat(11))
				.setLastName("Smith");

		Map<String, String> errorTesting = userValidator.validate(user);
		assertEquals("INVALID_LENGTH", errorTesting.get("firstName"));

	}

	@Test
	public void testValidateAndThrow_InvalidUsername_ExpectExceptionThrown() {
		when(userRepository.existsByUsername(any())).thenReturn(false);
		UserDto user = new UserDto()
				.setUsername(" ")
				.setFirstName("Jaden")
				.setLastName("Smith");

		try {
			userValidator.validateAndThrow(user);
			Assertions.fail();
		} catch (ValidationException e) {
			assertEquals("REQUIRED", e.getErrors().get("username"));
		}

	}

	@Test
	public void testValidateAndThrow_InvalidUsername_ExpectExceptionThrown2() {
		when(userRepository.existsByUsername(any())).thenReturn(false);
		UserDto user = new UserDto()
				.setUsername(" ")
				.setFirstName("Jaden")
				.setLastName("Smith");

		ValidationException e = assertThrows(ValidationException.class, () -> userValidator.validateAndThrow(user));
		assertEquals("REQUIRED", e.getErrors().get("username"));

	}

}
