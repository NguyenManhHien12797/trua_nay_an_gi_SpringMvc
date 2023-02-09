package shopbaeFood.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import shopbaeFood.model.Account;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.Status;
import shopbaeFood.model.UserForm;

public interface IAppUserSevice extends IGeneralService<AppUser> {

	@Override
	void save(AppUser appUser);

	@Override
	void update(AppUser appUser);

	@Override
	List<AppUser> findAll();

	@Override
	AppUser findById(Long id);

	AppUser findByName(String name);

	void updateStatus(Long id, Status status);

	void updateUserInfo(UserForm userForm, Account account, HttpSession session);

}
