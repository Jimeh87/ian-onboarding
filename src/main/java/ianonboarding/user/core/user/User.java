package ianonboarding.user.core.user;

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
@Table(name = "user")
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(of = "userId")
public class User {

	@Id
	@Type(type = "uuid-char")
	@Column(name = "user_id")
	private UUID userId;
	
	@Column(name = "username")
	private String username;

	@Column(name = "first_name")
	@Setter
	private String firstName;

	@Column(name = "last_name")
	@Setter
	private String lastName;
	

	User() {
	}

	public static User newInstance(String username) {
		User user = new User();
		user.userId = UUID.randomUUID();
		user.username = username;
		return user;
	}

}
