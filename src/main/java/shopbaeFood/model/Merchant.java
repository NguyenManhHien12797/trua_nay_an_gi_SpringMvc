package shopbaeFood.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DynamicUpdate
public class Merchant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String phone;
	private String address;

	private String avatar;
	private String openTime;
	private String closeTime;
	private String category;
	@Enumerated(EnumType.STRING)
	private Status status;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	@JsonBackReference
	private Account account;
	
	public Merchant() {
	}

	public Merchant(Long id, String name, String phone, String address, String avatar, String openTime,
			String closeTime, String category, Status status, Account account) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.avatar = avatar;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.category = category;
		this.status = status;
		this.account = account;
	}

	public Merchant(String address, String avatar, String name, String phone, Status status, String category, Account account) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.avatar = avatar;
		this.status = status;
		this.category = category;
		this.account = account;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
