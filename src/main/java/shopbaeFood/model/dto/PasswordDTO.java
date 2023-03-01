package shopbaeFood.model.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class PasswordDTO {
	@NotEmpty(message = "{currentPassword.not.empty}")
	private String currentPassword;
	@NotEmpty(message = "{newPassword.not.empty}")
	private String newPassword;
	@NotEmpty(message = "{confirmPassword.not.empty}")
	private String confirmPassword;
	
	public PasswordDTO() {
	}
	
	public PasswordDTO(String currentPassword, String newPassword, String confirmPassword) {
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	

}
