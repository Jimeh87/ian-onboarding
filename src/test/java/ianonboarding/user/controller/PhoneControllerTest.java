package ianonboarding.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import ianonboarding.user.controller.phoneController.PhoneDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PhoneControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testCreateUser_ValidPhone_ExpectCreate() {
		UserDto createdUser = new UserDto()
				.setUsername("BigWillyStyle")
				.setFirstName("Will")
				.setLastName("Smith");
		
		ResponseEntity<UserDto> response = restTemplate.postForEntity("/api/v1/users", createdUser, UserDto.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		createdUser = response.getBody();
		
		PhoneDto createdPhone = new PhoneDto()
				.setUserId(createdUser.getId())
				.setPhoneNumber("5551234567");
		
		ResponseEntity<PhoneDto> phoneResponse = restTemplate.postForEntity("/api/v1/users/" + createdUser.getId() + "/phones", createdPhone, PhoneDto.class);
		assertEquals(HttpStatus.CREATED, phoneResponse.getStatusCode());
	}
}
