package trua_nay_an_gi.model.dto;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import trua_nay_an_gi.model.validator.Phone;
import trua_nay_an_gi.model.validator.UserNameUnique;

public class AccountRegisterDTO {
	private Long id;
	@NotEmpty(message = "Tên người dùng không được để trống")
	@UserNameUnique
	private String userName;
	@NotEmpty(message = "Mật khẩu không được để trống")
	private String password;
	@NotEmpty(message = "Email không được để trống")
	@Email(message = "Email phải có @")
	private String email;
	@NotEmpty(message = "Tên chủ tài khoản không được để trống")
	private String name;
	@NotEmpty(message = "Số điện thoại không được để trống")
	@Phone
	private String phone;
	@NotEmpty(message = "Địa chỉ không được để trống")
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
