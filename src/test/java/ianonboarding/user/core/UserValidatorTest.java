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
	
	private UserService userService = mock(UserService.class);
	private UserValidator userValidator = new UserValidator(userService);

	@Test
	public void testValidate_NoUsername_ExpectUsernameRequired() {
		when(userService.userNameExists(any(), any())).thenReturn(false);
		UserDto userDto = new UserDto()
				.setUsername(null)
				.setFirstName("Will")
				.setLastName("Smith");

		Map<String, String> errorTesting = userValidator.validate(userDto);
		assertEquals("REQUIRED", errorTesting.get("username"));

	}

	@Test
	public void testValidate_FirstNameLengthTooLong_ExpectInvalidLength() {
		when(userService.userNameExists(any(), any())).thenReturn(false);
		UserDto user = new UserDto()
				.setUsername("BadActor")
				.setFirstName("Jaden".repeat(11))
				.setLastName("Smith");

		Map<String, String> errorTesting = userValidator.validate(user);
		assertEquals("INVALID_LENGTH", errorTesting.get("firstName"));

	}

	@Test
	public void testValidateAndThrow_InvalidUsername_ExpectExceptionThrown() {
		when(userService.userNameExists(any(), any())).thenReturn(false);
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
		when(userService.userNameExists(any(), any())).thenReturn(false);
		UserDto user = new UserDto()
				.setUsername(" ")
				.setFirstName("Jaden")
				.setLastName("Smith");

		ValidationException e = assertThrows(ValidationException.class, () -> userValidator.validateAndThrow(user));
		assertEquals("REQUIRED", e.getErrors().get("username"));

	}
	
	@Test
	public void testValidateUniqueUsername_NonUniqueUsername_ExpectNonUniqueExceptionThrown() {
		when(userService.userNameExists(any(), any())).thenReturn(true);
		UserDto user = new UserDto()
				.setUsername("BadActor")
				.setFirstName("Jaden")
				.setLastName("Smith");
		
		Map<String, String> errorTesting = userValidator.validate(user);
		assertEquals("NOT_UNIQUE", errorTesting.get("username"));
	}
	
	@Test
	public void testValidateUniqueUsernameWithUpdate_UpdatingFirstName_ExpectNoExceptionsThrown() {
		UserDto user = new UserDto()
				.setUsername("BadActor")
				.setFirstName("Jaden")
				.setLastName("Smith");
		
		when(userService.userNameExists(user.getUsername(), user.getId())).thenReturn(false);

		user.setFirstName("Lloyd");
		
		Map<String, String> errorTesting = userValidator.validate(user);
		assertEquals("Lloyd", user.getFirstName());
		assertEquals(null, errorTesting.get("username"));
	}

}
