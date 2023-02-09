package shopbaeFood.model.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import shopbaeFood.model.validator.Phone;
import shopbaeFood.model.validator.UserNameUnique;

public class AccountRegisterDTO {
	private Long id;
	@NotEmpty(message = "{userName.not.empty}")
	@UserNameUnique
	private String userName;
	@NotEmpty(message = "{password.not.empty}")
	private String password;
	@NotEmpty(message = "{mail.not.empty}")
	@Email(message = "{mail.type}")
	private String email;
	@NotEmpty(message = "{name.not.empty}")
	private String name;
	@NotEmpty(message = "{phone.not.empty}")
	@Phone
	private String phone;
	@NotEmpty(message = "{address.not.empty}")
	private String address;

	public AccountRegisterDTO() {
	}

	public AccountRegisterDTO(Long id, String userName, String password, String email, String name, String phone,
			String address) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
