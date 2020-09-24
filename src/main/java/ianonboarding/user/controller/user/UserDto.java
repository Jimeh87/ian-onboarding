package ianonboarding.user.controller.user;

import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {
	private UUID userId;
	private String username;
	private String firstName;
	private String lastName;
}
