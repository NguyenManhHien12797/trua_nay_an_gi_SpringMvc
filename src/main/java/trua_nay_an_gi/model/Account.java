package trua_nay_an_gi.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private boolean isEnabled;
    private String email;
    private String otp;
    @OneToMany(mappedBy = "account")
    private Set<AccountRoleMap> accountRoleMapSet;
    
	public Account() {
	}


	

	public Account(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}


	public Account(Long id, String userName, String password, boolean isEnabled, String email, String otp,
			Set<AccountRoleMap> accountRoleMapSet) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.isEnabled = isEnabled;
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

	
	
}
