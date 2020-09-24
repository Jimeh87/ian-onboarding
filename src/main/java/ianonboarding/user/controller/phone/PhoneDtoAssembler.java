package ianonboarding.user.controller.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import ianonboarding.user.controller.validation.ValidationDto;
import ianonboarding.user.core.phone.Phone;
import ianonboarding.user.core.phone.PhoneService;
import ianonboarding.user.core.validation.Validation;

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
				.setVerificationTwilio(phone.getVerificationTwilio())
				.setVerificationSId(phone.getVerificationSId());
	}
	
	public Phone disassemble(PhoneDto phoneDto) {
		return Phone.newInstance(phoneDto.getUserId(), phoneDto.getPhoneNumber(), phoneDto.getPrimaryNumber(), phoneDto.getVerificationTwilio(), phoneDto.getVerificationSId());
	}
	
//	public PhoneDto assembleVerification(Phone phone, Validation validation) {
//		String replyCode = phoneService.verifyPhoneNumber(phone, phone.getVerificationSId(), validation.getCode());
//		if(replyCode.equals("approved")) {
//			return new PhoneDto()
//					.setPhoneId(phone.getPhoneId())
//					.setUserId(phone.getUserId())
//					.setPhoneNumber(phone.getPhoneNumber())
//					.setPrimaryNumber(phone.getPrimaryNumber())
//					.setVerificationSId(phone.getVerificationSId())
//					.setVerificationTwilio(true);
//		} else {
//			return new PhoneDto()
//					.setPhoneId(phone.getPhoneId())
//					.setUserId(phone.getUserId())
//					.setPhoneNumber(phone.getPhoneNumber())
//					.setPrimaryNumber(phone.getPrimaryNumber())
//					.setVerificationSId(phone.getVerificationSId())
//					.setVerificationTwilio(false);
//		}
//
//	}
	
//	public Validation disassembleVerification(ValidationDto validationDto) {
//		return Validation.newInstance(validationDto.getCode());
//	}
}