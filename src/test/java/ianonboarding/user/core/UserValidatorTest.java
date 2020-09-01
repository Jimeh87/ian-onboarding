package ianonboarding.user.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ianonboarding.user.controller.UserDto;

public class UserValidatorTest {

	private UserValidator userValidator = new UserValidator();

	@Test
	public void testValidate_NoUsername_ExpectUsernameRequired() {
		UserDto userDto = new UserDto().setUsername(null).setFirstName("Will").setLastName("Smith");

		Map<String, String> errorTesting = userValidator.validate(userDto);
		assertEquals("REQUIRED", errorTesting.get("username"));

	}

	// TODO: Length of Firstname test
	@Test
	public void testValidate_FirstNameLengthTooLong_ExpectInvalidLength() {
		UserDto user = new UserDto().setUsername("BadActor").setFirstName("Jaden".repeat(11)).setLastName("Smith");

		Map<String, String> errorTesting = userValidator.validate(user);
		assertEquals("INVALID_LENGTH", errorTesting.get("firstName"));

	}

	// TODO: Validate and throws test,
	@Test
	public void testValidateAndThrow_InvalidUsername_ExpectExceptionThrown() {
		UserDto user = new UserDto().setUsername(" ").setFirstName("Jaden").setLastName("Smith");

		try {
			userValidator.validateAndThrow(user);
			Assertions.fail();
		} catch (ValidationException e) {
			assertEquals("REQUIRED", e.getErrors().get("username"));
		}

	}

	@Test
	public void testValidateAndThrow_InvalidUsername_ExpectExceptionThrown2() {
		UserDto user = new UserDto().setUsername(" ").setFirstName("Jaden").setLastName("Smith");

		ValidationException e = assertThrows(ValidationException.class, () -> userValidator.validateAndThrow(user));
		assertEquals("REQUIRED", e.getErrors().get("username"));

	}

}
