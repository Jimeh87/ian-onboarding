package ianonboarding.user.controller.phoneController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ianonboarding.user.core.phone.Phone;
import ianonboarding.user.core.phone.PhoneService;

@Component
public class PhoneDtoAssembler {
	
	@Autowired
	private PhoneService phoneService;
	
	public PhoneDto assemble(Phone phone) {
		return new PhoneDto()
				.setPhoneId(phone.getPhoneId())
				.setUserId(phone.getUserId())
				.setPhoneNumber(phone.getPhoneNumber());
	}
	
	public Phone disassemble(PhoneDto phoneDto) {
		Phone phone = phoneDto.getPhoneId() != null
				? phoneService.getPhone(phoneDto.getPhoneId())
				: Phone.newInstance(phoneDto.getUserId());
				
		return phone
				.setPhoneNumber(phoneDto.getPhoneNumber());
	}
}
