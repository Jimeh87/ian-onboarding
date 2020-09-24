package ianonboarding.user.core.phone;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ianonboarding.user.controller.phone.PhoneDto;
import ianonboarding.user.verfication.TwilioVerification;

@Service
public class PhoneService {
	
	@Autowired
	private PhoneRepository phoneRepository;
	
	public List<Phone> getPhones(){
		return phoneRepository.findAll();
	}

	public Phone getPhone(UUID id) {
		return phoneRepository.getOne(id);
	}
	
	public Optional<Phone> findPhone(UUID id){
		return phoneRepository.findById(id);
	}
	
	public Phone save(Phone phone) {
		// send phone verification code via twilio and save the SID
//		phone.setVerificationSId(sendVerification(phone.getPhoneNumber()));
		
//		// if no phones are on user make this phone primary
//		// use phoneRepository.findAll().size(); find size of the phones list
		if(phoneRepository.findAll().isEmpty() == true) {
			phone.setPrimaryNumber(true);
		} else if(phone.getPrimaryNumber() == true) {
			phoneRepository.findAll().forEach(p -> p.setPrimaryNumber(false));
		}	else {
			phone.setPrimaryNumber(false);
		}
		return phoneRepository.save(phone);
	}
	
//	public String sendVerification(String phoneNumber) {
//		TwilioVerification twilioVerification = new TwilioVerification();
//		return twilioVerification.sendVerificationCode(phoneNumber);
//	}
//	
//	public String verifyPhoneNumber(Phone phone, String sId, String code) {
////		if(sendVerification(phone.getPhoneNumber()).equals("approved")){
////			phone.setVerificationTwilio(true);
////		} else {
////			phone.setVerificationTwilio(false);
////		}
//		TwilioVerification twilioVerification = new TwilioVerification();
//		return twilioVerification.verify(sId, phone.getPhoneNumber(), code);
//	}
	
	public void delete (UUID id) {
		phoneRepository.deleteById(id);
	}
	
	public void deleteAll() {
		phoneRepository.deleteAll();
	}

	public List<Phone> getPhonesByUserId(UUID userId) {
		return phoneRepository.findPhonesByUserId(userId);
	}
}
