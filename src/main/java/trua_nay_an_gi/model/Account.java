package trua_nay_an_gi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


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
    
    
	public Account() {
	}


	public Account(Long id, String userName, String password, boolean isEnabled, String email, String otp) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.isEnabled = isEnabled;
		this.email = email;
		this.otp = otp;
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
