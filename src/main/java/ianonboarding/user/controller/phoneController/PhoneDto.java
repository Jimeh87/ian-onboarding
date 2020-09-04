package ianonboarding.user.controller.phoneController;

import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PhoneDto {
	
	private UUID phoneId;
	private UUID userId;
	private String phoneNumber;
}
