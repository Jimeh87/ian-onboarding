package ianonboarding.user.core.phone;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import ianonboarding.user.controller.phoneController.PhoneDto;
import ianonboarding.user.core.ValidationException;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PhoneValidator {
	
	private PhoneService phoneService;
	
	public void validateAndThrow(PhoneDto phoneDto) {
		Map<String, String> errors = validate(phoneDto);
		
		if (!errors.isEmpty()) {
			throw new ValidationException(errors);
		}
	}
	
	public Map<String, String> validate(PhoneDto phoneDto){
		Map<String, String> errors = new LinkedHashMap<>();
		if(StringUtils.isBlank(phoneDto.getPhoneNumber())) {
			errors.put("phoneNumber", "REQUIRED");
		}
		return errors;
	}

}
