package ianonboarding.user.verfication;

import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

@Component
public class TwilioVerification {
	private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    
    public TwilioVerification(){
    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    	// Creating a new verification service https://www.twilio.com/docs/verify/api/service
    }
    
    private Service serviceInit() {
    	Service service = Service.creator("My Verify Service").create();
        return service;
    }
    
    // only exposed methods.. make the other ones private
    public String sendVerificationCode(String phoneNumber) {
//  	to send the verification code
    	TwilioVerification twilioVerification = new TwilioVerification();
    	Service service = twilioVerification.serviceInit();
    	Verification verification = twilioVerification.verificationSetup(service, phoneNumber);
    	twilioVerification.verificationFetcher(service.getSid(), verification, phoneNumber);
    	return service.getSid();
    }
    
    public String verify(String serviceSid, String phoneNumber, String submittedCode) {
    	TwilioVerification twilioVerification = new TwilioVerification();
    	return twilioVerification.verificationCheck(serviceSid, phoneNumber, submittedCode);
//  	verify the code that the user sends back
    	
	}
    
    // Verification Setup https://www.twilio.com/docs/verify/api/verification?code-sample=code-start-a-verification-with-sms&code-language=Java&code-sdk-version=7.x
    private Verification verificationSetup(Service service, String phoneNumber) {
    	Verification verification = Verification.creator(
    			service.getSid(),
    			"+1" + phoneNumber,
    			"sms")
    			.create();
        return verification;
    }
    
    private Verification verificationFetcher(String serviceSid, Verification verification, String phoneNumber) {
    	verification = Verification.fetcher(
    			serviceSid,
        		"+1" + phoneNumber)
    			.fetch();
    	return verification;
    }
    
 // Verification check https://www.twilio.com/docs/verify/api/verification-check
    private String verificationCheck(String serviceSid, String phoneNumber, String submittedCode) {
    	VerificationCheck verificationCheck = VerificationCheck.creator(
    			  serviceSid,
    			  submittedCode)
    			  .setTo("+1" + phoneNumber)
    			  .create();
    	System.out.println(verificationCheck.getStatus());
    	return verificationCheck.getStatus();
    }

}
