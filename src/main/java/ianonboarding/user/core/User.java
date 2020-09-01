package ianonboarding.user.core;

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
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
public class User {

	@Id
	@Type(type = "uuid-char")
	@Column(name = "user_id")
	private UUID id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	User() {
	}

	public static User newInstance() {
		User user = new User();
		user.id = UUID.randomUUID();
		return user;
	}

}
