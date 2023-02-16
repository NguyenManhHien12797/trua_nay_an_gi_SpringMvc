package shopbaeFood.service;

import javax.servlet.http.HttpSession;

import shopbaeFood.model.Account;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.Status;
import shopbaeFood.model.UserForm;

public interface IAppUserSevice extends IGeneralService<AppUser> {

	/**
	 * This method is used to find AppUser by name
	 * @return AppUser
	 */
	AppUser findByName(String name);

	/**
	 * This method is used to update status 
	 */
	void updateStatus(Long id, Status status);

	/**
	 * This method is used to update user info and set attribute user, avatar and userName
	 */
	void updateUserInfo(UserForm userForm, Account account, HttpSession session);

}
