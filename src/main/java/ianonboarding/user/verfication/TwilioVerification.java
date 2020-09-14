package ianonboarding.user.verfication;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import ianonboarding.user.controller.phone.PhoneDto;

@Component
public class TwilioVerification {
	private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    
    public TwilioVerification(){
    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    	// Creating a new verification service https://www.twilio.com/docs/verify/api/service
    }
    
    public Service serviceInit() {
    	Service service = Service.creator("My Verify Service").create();
        System.out.println(service.getSid());
        System.out.println(service.getAccountSid());
        return service;
    }
    
    // only exposed methods.. make the other ones private
//    public String sendVerificationCode(String phoneNumber) {
//    	
//    }
//    
//    public void verify(String phoneNumber, String verificationCode) {
//    	
//    }
    
    // Verification Setup https://www.twilio.com/docs/verify/api/verification?code-sample=code-start-a-verification-with-sms&code-language=Java&code-sdk-version=7.x
    public Verification verificationSetup(Service service, PhoneDto phoneDto) {
    	Verification verification = Verification.creator(
    			service.getSid(),
    			"+1" + phoneDto.getPhoneNumber(),
    			"sms")
    			.create();

        System.out.println(verification.getSid());
        return verification;
    }
    
    public Verification verificationFetcher(Service service,Verification verification, PhoneDto phoneDto) {
    	verification = Verification.fetcher(
    			service.getSid(),
        		"+1" + phoneDto.getPhoneNumber())
    			.fetch();

        System.out.println(verification.getStatus());
    	return verification;
    }
    
 // Verification check https://www.twilio.com/docs/verify/api/verification-check
    public String verificationCheck(Service service, Verification verification, PhoneDto phoneDto) {
    	String userCode = null;
    	Scanner scanner = new Scanner(System.in);
    	userCode = scanner.nextLine();
    	VerificationCheck verificationCheck = VerificationCheck.creator(
    			  service.getSid(),
    			  userCode)
    			  .setTo("+1" + phoneDto.getPhoneNumber()).create();

    	System.out.println(verificationCheck.getStatus());
    	return verificationCheck.getStatus();
    }

}
