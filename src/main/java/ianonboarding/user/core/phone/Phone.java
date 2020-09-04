package ianonboarding.user.core.phone;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import ianonboarding.user.controller.UserDto;
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

	Phone() {
	}

	public static Phone newInstance(UUID userId) {
		Phone phone = new Phone();
		phone.phoneId = UUID.randomUUID();
		phone.userId = userId;
		return phone;
	}
}
