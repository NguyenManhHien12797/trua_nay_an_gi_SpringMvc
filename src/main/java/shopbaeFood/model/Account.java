package shopbaeFood.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "account")
@DynamicUpdate
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String userName;
	private String password;
	private boolean isEnabled;
	private String email;
	private String otp;
	@Column(name = "first_login")
	@ColumnDefault("1")
	private boolean firstLogin;
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;
	@Column(name = "failed_attempt")
	private int failedAttempt;
	@Column(name = "lock_time")
	private Date lockTime;
	@OneToOne(mappedBy = "account")
	private AppUser user;
	@OneToOne(mappedBy = "account")
	private Merchant merchant;
	@OneToMany(mappedBy = "account")
	@JsonManagedReference
	private Set<AccountRoleMap> accountRoleMapSet;

	public Account() {
	}

	public Account(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public Account(String userName, String password, boolean isEnabled, boolean firstLogin, String email) {
		this.userName = userName;
		this.password = password;
		this.isEnabled = isEnabled;
		this.firstLogin = firstLogin;
		this.email = email;
	}

	public Account(Long id, String userName, String password, boolean isEnabled,boolean firstLogin, String email, String otp,
			Set<AccountRoleMap> accountRoleMapSet) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.isEnabled = isEnabled;
		this.firstLogin = firstLogin;
		this.email = email;
		this.otp = otp;
		this.accountRoleMapSet = accountRoleMapSet;
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

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Set<AccountRoleMap> getAccountRoleMapSet() {
		return accountRoleMapSet;
	}

	public void setAccountRoleMapSet(Set<AccountRoleMap> accountRoleMapSet) {
		this.accountRoleMapSet = accountRoleMapSet;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public int getFailedAttempt() {
		return failedAttempt;
	}

	public void setFailedAttempt(int failedAttempt) {
		this.failedAttempt = failedAttempt;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
	
	
	

}
