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
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String address;
	private String phone;
	private String avatar;
	@Enumerated(EnumType.STRING)
	private Status status;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", nullable = true)
	@JsonBackReference
	private Account account;

	public AppUser() {
	}

	public AppUser(Long id, String name, String address, String phone, String avatar, Status status, Account account) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.avatar = avatar;
		this.status = status;
		this.account = account;
	}

	public AppUser(String address, String avatar, String name, String phone, Status status, Account account) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.avatar = avatar;
		this.status = status;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

}
