package ianonboarding.user.controller;

import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {
	private UUID id;
	private String username;
	private String firstName;
	private String lastName;
}
