package ianonboarding.user.core;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ValidationException extends RuntimeException {

	@Getter
	private Map<String, String> errors;
	
}
