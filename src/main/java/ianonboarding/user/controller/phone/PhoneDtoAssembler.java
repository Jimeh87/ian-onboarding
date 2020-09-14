package ianonboarding.user.controller.phone;

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
				.setPhoneNumber(phone.getPhoneNumber())
				.setPrimaryNumber(phone.getPrimaryNumber())
				.setVerificationTwilio(phone.getVerificationTwilio());
	}
	
	public Phone disassemble(PhoneDto phoneDto) {
		return Phone.newInstance(phoneDto.getUserId(), phoneDto.getPhoneNumber());
	}
}