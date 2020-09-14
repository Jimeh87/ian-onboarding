package ianonboarding.user.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import ianonboarding.user.controller.user.UserDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testCreateUser_ValidUser_ExpectCreated() {
		UserDto userDto = new UserDto()
				.setUsername("BigWillyStyle")
				.setFirstName("Will")
				.setLastName("Smith");
		
		ResponseEntity<UserDto> response = restTemplate.postForEntity("/api/v1/users", userDto, UserDto.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		UserDto createdUser = response.getBody();
		assertNotNull(createdUser);
		assertAll(
				() -> assertNotNull(createdUser.getId(), "id"),
				() -> assertEquals(userDto.getUsername(), createdUser.getUsername(), "username"),
				() -> assertEquals(userDto.getFirstName(), createdUser.getFirstName(), "firstName"),
				() -> assertEquals(userDto.getLastName(), createdUser.getLastName(), "lastName")
		);
	}
	
	@Test
	public void testCreateUser_UsernameIsTooLong_ExpectValidationError() {
		UserDto userDto = new UserDto()
				.setUsername("ThisIsTheStoryAllAboutHowMyLifeGotFlippedRightUpSideDown")
				.setFirstName("Will")
				.setLastName("Smith");
		
		ResponseEntity<Map> response = restTemplate.postForEntity("/api/v1/users", userDto, Map.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		Map<String, String> errors = response.getBody();
		assertEquals(1, errors.size());
		assertEquals("Invalid length", errors.get("username"));
	}

	@Test
	public void testGetUser_OneUser_ExpectUserReturned() {
		UserDto user = createUser(new UserDto()
				.setUsername("BadActor")
				.setFirstName("Jaden")
				.setLastName("Smith"));
		
		ResponseEntity<UserDto> response = restTemplate.getForEntity("/api/v1/users/" + user.getId(), UserDto.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(user.getId(), response.getBody().getId());
	}
	
	@Test
	public void testGetUsers_TwoUser_ExpectTwoUsersReturned() {
		createUser(new UserDto()
				.setUsername("BasketHoopsGuy2400")
				.setFirstName("Kyle")
				.setLastName("Lowry"));
		
		createUser(new UserDto()
				.setUsername("DunkMaster45")
				.setFirstName("Vince")
				.setLastName("Carter"));
		
		ResponseEntity<UserDto[]> response = restTemplate.getForEntity("/api/v1/users/", UserDto[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().length);
	}
	
	@Test
	public void testPut_OneUserNameChanged_ExpectNewNameReturned() {
		UserDto user = createUser(new UserDto()
				.setUsername("ItMe")
				.setFirstName("Ian")
				.setLastName("Quach"));
		
		ResponseEntity<UserDto> response = restTemplate.getForEntity("/api/v1/users/" + user.getId(), UserDto.class);
		restTemplate.put("/api/v1/users/" + user.getId(), user.setFirstName("Lloyd"));
		response = restTemplate.getForEntity("/api/v1/users/" + user.getId(), UserDto.class);
		assertEquals(user.getFirstName(), response.getBody().getFirstName());
	}
	
	@Test
	public void testDeleteUser_OneUserDeleted_ExpectZeroUsersReturned() {
		UserDto user = createUser(new UserDto()
				.setUsername("ItMe")
				.setFirstName("Ian")
				.setLastName("Quach"));
		
		ResponseEntity<UserDto[]> response = restTemplate.getForEntity("/api/v1/users/", UserDto[].class); // Returns the user as a UserDto object
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody()); // make sure you're actually receiving the users/that they exist
		restTemplate.delete("/api/v1/users/" + user.getId(), UserDto.class);
		response = restTemplate.getForEntity("/api/v1/users/", UserDto[].class);
		assertEquals(0, response.getBody().length);
	}
	
	private UserDto createUser(UserDto userDto) {
		return restTemplate.postForEntity("/api/v1/users", userDto, UserDto.class).getBody();
	}

}
