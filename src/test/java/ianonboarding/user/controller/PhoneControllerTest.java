package ianonboarding.user.controller;

import static org.junit.jupiter.api.Assertions.*;

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
		UserDto createdUser = createUser(new UserDto()
				.setUsername("BigWillyStyle")
				.setFirstName("Will")
				.setLastName("Smith"));
		
		ResponseEntity<UserDto> response = restTemplate.getForEntity("/api/v1/users/" + createdUser.getId(), UserDto.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		PhoneDto createdPhone = new PhoneDto()
				.setUserId(createdUser.getId())
				.setPhoneNumber("5551234567")
				.setPrimaryNumber(true);
		
		ResponseEntity<PhoneDto> phoneResponse = restTemplate.postForEntity("/api/v1/users/" + createdUser.getId() + "/phones", createdPhone, PhoneDto.class);
		assertEquals(HttpStatus.CREATED, phoneResponse.getStatusCode());
	}
	
	@Test
	public void testGetPhoneNumber_OnePhoneNumber_ExpectPhoneNumberReturned() {
		UserDto createdUser = createUser(new UserDto()
				.setUsername("BigWillyStyle")
				.setFirstName("Will")
				.setLastName("Smith"));
		
		ResponseEntity<UserDto> response = restTemplate.getForEntity("/api/v1/users/" + createdUser.getId(), UserDto.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		PhoneDto createdPhone = new PhoneDto()
				.setUserId(createdUser.getId())
				.setPhoneNumber("5551234567")
				.setPrimaryNumber(false);
		
		ResponseEntity<PhoneDto> phoneResponse = restTemplate.postForEntity("/api/v1/users/" + createdUser.getId() + "/phones", createdPhone, PhoneDto.class);
		assertEquals(HttpStatus.CREATED, phoneResponse.getStatusCode());
		createdPhone = phoneResponse.getBody();
		assertNotNull(restTemplate.getForEntity("/api/v1/users/" + createdUser.getId() + "/phones/" + createdPhone.getPhoneNumber(), PhoneDto.class));
	}
	
	@Test
	public void testGetPhoneNumbers_TwoPhone_ExpectTwoPhonesReturned() {
		UserDto createdUser = createUser(new UserDto()
				.setUsername("BigWillyStyle")
				.setFirstName("Will")
				.setLastName("Smith"));
		
		ResponseEntity<UserDto> response = restTemplate.getForEntity("/api/v1/users/" + createdUser.getId(), UserDto.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		createPhone(createdUser, new PhoneDto()
				.setUserId(createdUser.getId())
				.setPhoneNumber("5551234567")
				.setPrimaryNumber(true));
		createPhone(createdUser, new PhoneDto()
				.setUserId(createdUser.getId())
				.setPhoneNumber("6661234567")
				.setPrimaryNumber(true));
		
		ResponseEntity<PhoneDto[]> phoneResponse = restTemplate.getForEntity("/api/v1/users/" + createdUser.getId() + "/phones/", PhoneDto[].class);
		assertEquals(HttpStatus.OK, phoneResponse.getStatusCode());
		assertNotNull(phoneResponse.getBody());
		assertEquals(2, phoneResponse.getBody().length);
	}
	
	@Test
	public void testPut_OnePhoneNumberChanged_ExectNewPhoneNumberReturned() {
		UserDto createdUser = createUser(new UserDto()
				.setUsername("BigWillyStyle")
				.setFirstName("Will")
				.setLastName("Smith"));
		
		ResponseEntity<UserDto> response = restTemplate.getForEntity("/api/v1/users/" + createdUser.getId(), UserDto.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		PhoneDto createdPhone = new PhoneDto()
				.setUserId(createdUser.getId())
				.setPhoneNumber("5551234567")
				.setPrimaryNumber(false);

		ResponseEntity<PhoneDto> phoneResponse = restTemplate.postForEntity("/api/v1/users/" + createdUser.getId() + "/phones/", createdPhone, PhoneDto.class);
		assertEquals(HttpStatus.CREATED, phoneResponse.getStatusCode());
		createdPhone = phoneResponse.getBody();
		
		restTemplate.put("/api/v1/users/" + createdUser.getId() + "/phones/" + createdPhone.getPhoneId(), createdPhone.setPhoneNumber("6661234567"));
		phoneResponse = restTemplate.getForEntity("/api/v1/users/" + createdPhone.getUserId() + "/phones/" + createdPhone.getPhoneId(), PhoneDto.class);
		assertEquals(createdPhone.getPhoneNumber(), phoneResponse.getBody().getPhoneNumber());
	}

	@Test
	public void testPutPrimaryPhoneNumber_SetPrimaryPhone_ExpectPrimaryPhoneNumberReturned() {
		UserDto createdUser = createUser(new UserDto()
				.setUsername("BigWillyStyle")
				.setFirstName("Will")
				.setLastName("Smith"));
		ResponseEntity<UserDto> response = restTemplate.getForEntity("/api/v1/users/" + createdUser.getId(), UserDto.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		PhoneDto createdPhone = new PhoneDto()
				.setUserId(createdUser.getId())
				.setPhoneNumber("5551234567")
				.setPrimaryNumber(false);
		ResponseEntity<PhoneDto> phoneResponse = restTemplate.postForEntity("/api/v1/users/" + createdUser.getId() + "/phones", createdPhone, PhoneDto.class);
		assertEquals(HttpStatus.CREATED, phoneResponse.getStatusCode());
		createdPhone = phoneResponse.getBody();
		
		restTemplate.put("/api/v1/users/" + createdUser.getId() + "/phones/" + createdPhone.getPhoneId(), createdPhone.setPrimaryNumber(true));
		phoneResponse = restTemplate.getForEntity("/api/v1/users/" + createdPhone.getUserId() + "/phones/" + createdPhone.getPhoneId(), PhoneDto.class);
		assertEquals(true, phoneResponse.getBody().getPrimaryNumber());
	}
	
	@Test
	public void testDeletePhone_OnePhoneDeleted_ExpectZeroPhonesReturned() {
		UserDto createdUser = createUser(new UserDto()
				.setUsername("BigWillyStyle")
				.setFirstName("Will")
				.setLastName("Smith"));
		
		PhoneDto createdPhone = createPhone(createdUser,new PhoneDto()
				.setUserId(createdUser.getId())
				.setPhoneNumber("5551234567")
				.setPrimaryNumber(false));
		ResponseEntity<PhoneDto[]> phoneResponse = restTemplate.getForEntity("/api/v1/users/" + createdUser.getId() + "/phones/", PhoneDto[].class); // Returns the user as a UserDto object
		assertEquals(HttpStatus.OK, phoneResponse.getStatusCode());
		assertNotNull(phoneResponse.getBody()); // make sure you're actually receiving the users/that they exist
		restTemplate.delete("/api/v1/users/" + createdUser.getId() + "/phones/" + createdPhone.getPhoneId(), PhoneDto.class);
		phoneResponse = restTemplate.getForEntity("/api/v1/users/" + createdUser.getId() + "/phones/", PhoneDto[].class); // Returns the user as a UserDto object
		assertEquals(0, phoneResponse.getBody().length);
	}
	
	private UserDto createUser(UserDto userDto) {
		return restTemplate.postForEntity("/api/v1/users", userDto, UserDto.class).getBody();
	}
	private PhoneDto createPhone(UserDto userDto, PhoneDto phoneDto) {
		return restTemplate.postForEntity("/api/v1/users/" + userDto.getId() + "/phones", phoneDto, PhoneDto.class).getBody();
	}
}
