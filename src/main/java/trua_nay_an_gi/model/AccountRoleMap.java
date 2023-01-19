package trua_nay_an_gi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="AccountRoleMap")
public class AccountRoleMap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "account_id")
	@JsonBackReference
	private Account account;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private AppRoles role;

	public AccountRoleMap() {
	}

	public AccountRoleMap(Account account, AppRoles role) {
		this.account = account;
		this.role = role;
	}

	public AccountRoleMap(Long id, Account account, AppRoles role) {
		this.id = id;
		this.account = account;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public AppRoles getRole() {
		return role;
	}

	public void setRole(AppRoles role) {
		this.role = role;
	}

}
