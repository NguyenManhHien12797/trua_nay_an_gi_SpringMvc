package shopbaeFood.model;

import org.springframework.web.multipart.MultipartFile;

public class UserForm {

	private Long id;
	private String name;
	private String address;
	private String phone;
	private MultipartFile avatar;
	private String status;

	private Account account;

	public UserForm() {
	}

	public UserForm(Long id, String name, String address, String phone, MultipartFile avatar, String status,
			Account account) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.avatar = avatar;
		this.status = status;
		this.account = account;
	}

	public UserForm(Long id, String name, String phone, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
