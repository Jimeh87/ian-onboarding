package ianonboarding.user.core.phone;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "phone")
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(of = "phoneId") // compare based on phoneId
public class Phone {

	@Id
	@Type(type = "uuid-char") // always indicate a UUID with uuid-char otherwise it is unhappy
	@Column(name = "phone_id")
	private UUID phoneId;

	@Type(type = "uuid-char")
	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "phone_number")
	@Setter
	private String phoneNumber;
	
	@Column(name = "primary_number")
	@Setter
	private Boolean primaryNumber;
	
	@Column(name = "verification_twilio")
	@Setter
	private Boolean verificationTwilio;
	
	@Column(name = "verification_sid")
	@Setter
	private String verificationSId;

	Phone() {
	}

	public static Phone newInstance(UUID userId, String phoneNumber, Boolean primaryNumber, Boolean verificationTwilio, String verificationSId) {
		Phone phone = new Phone();
		phone.phoneId = UUID.randomUUID();
		phone.userId = userId;
		phone.phoneNumber = phoneNumber;
		phone.primaryNumber = primaryNumber;
		phone.verificationTwilio = verificationTwilio;
		phone.verificationSId = verificationSId;
		return phone;
	}
}
