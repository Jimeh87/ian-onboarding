package ianonboarding.user.verification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.verify.v2.Service;

import ianonboarding.user.controller.phone.PhoneDto;
import ianonboarding.user.controller.user.UserDto;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TwilioVerificationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testValidateWithTwilio_OnePhoneNumberValidated_ExpectApproved() {
		ResponseEntity<UserDto> createdUser = createUser(new UserDto()
				.setUsername("iquach")
				.setFirstName("Ian")
				.setLastName("Quach"));
		
		ResponseEntity<UserDto> response = createdUser;
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		ResponseEntity<PhoneDto> createdPhone = createPhone(createdUser.getBody(),new PhoneDto()
				.setUserId(createdUser.getBody().getUserId())
				.setPhoneNumber("3065517518") // add that in if running the test
				.setPrimaryNumber(true));
		ResponseEntity<PhoneDto> phoneResponse = createdPhone;
		assertEquals(HttpStatus.CREATED, phoneResponse.getStatusCode());
		
//		ResponseEntity<PhoneDto> createdPhone2 = createPhone(createdUser.getBody(),new PhoneDto()
//				.setUserId(createdUser.getBody().getId())
//				.setPhoneNumber("3065508233") // add that in if running the test
//				.setPrimaryNumber(true));
//		ResponseEntity<PhoneDto> phoneResponse2 = createdPhone2;
//		assertEquals(HttpStatus.CREATED, phoneResponse2.getStatusCode());
//		ResponseEntity<PhoneDto[]> phoneResponse3 = restTemplate.getForEntity("/api/v1/users/" + createdUser.getBody().getId() + "/phones/", PhoneDto[].class);
		
		assertEquals(true,createdPhone.getBody().getVerificationTwilio());
//		assertEquals(true,createdPhone2.getBody().getVerificationTwilio());
	}
	
	private ResponseEntity<UserDto> createUser(UserDto userDto) {
		return restTemplate.postForEntity("/api/v1/users", userDto, UserDto.class);
	}
	private ResponseEntity<PhoneDto> createPhone(UserDto userDto, PhoneDto phoneDto) {
		return restTemplate.postForEntity("/api/v1/users/" + userDto.getUserId() + "/phones", phoneDto, PhoneDto.class);
	}
}
