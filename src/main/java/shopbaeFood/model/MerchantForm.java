package shopbaeFood.model;

import org.springframework.web.multipart.MultipartFile;

public class MerchantForm {

	private Long id;
	private String name;
	private String phone;
	private String address;

	private MultipartFile avatar;
	private String openTime;
	private String closeTime;

	private String status;
	private Account account;

	public MerchantForm() {
	}

	public MerchantForm(Long id, String name, String phone, String address, MultipartFile avatar, String openTime,
			String closeTime, String status, Account account) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.avatar = avatar;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.status = status;
		this.account = account;
	}

	public MerchantForm(Long id, String name, String address, String phone, String openTime, String closeTime) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.openTime = openTime;
		this.closeTime = closeTime;
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

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
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
