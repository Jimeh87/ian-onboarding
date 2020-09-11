package ianonboarding.user.verification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;

import ianonboarding.user.controller.UserDto;
import ianonboarding.user.controller.phoneController.PhoneDto;
import ianonboarding.user.verfication.TwilioVerification;


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
				.setUserId(createdUser.getBody().getId())
				.setPhoneNumber("REAL_PHONE_NUMBER") // add that in if running the test
				.setPrimaryNumber(true));
		
		ResponseEntity<PhoneDto> phoneResponse = createdPhone;
		assertEquals(HttpStatus.CREATED, phoneResponse.getStatusCode());
		
		TwilioVerification twilioVerification = new TwilioVerification();
		Service service = twilioVerification.serviceInit();
		Verification verification = twilioVerification.verificationSetup(service, createdPhone.getBody());
		verification = twilioVerification.verificationFetcher(service, verification, createdPhone.getBody());
		
		if(twilioVerification.verificationCheck(service, verification, createdPhone.getBody()).equals("approved")) {
			restTemplate.put("/api/v1/users/" + createdUser.getBody().getId() + "/phones/" + createdPhone.getBody().getPhoneId()
					, createdPhone.getBody().setVerificationTwilio(true));
		} else {
			restTemplate.put("/api/v1/users/" + createdUser.getBody().getId() + "/phones/" + createdPhone.getBody().getPhoneId()
					, createdPhone.getBody().setVerificationTwilio(false));
		}
		assertEquals(true,createdPhone.getBody().getVerificationTwilio());
		
	}
	
	private ResponseEntity<UserDto> createUser(UserDto userDto) {
		return restTemplate.postForEntity("/api/v1/users", userDto, UserDto.class);
	}
	private ResponseEntity<PhoneDto> createPhone(UserDto userDto, PhoneDto phoneDto) {
		return restTemplate.postForEntity("/api/v1/users/" + userDto.getId() + "/phones", phoneDto, PhoneDto.class);
	}
}
