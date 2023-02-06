package trua_nay_an_gi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.MerchantForm;
import trua_nay_an_gi.model.UserForm;

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

	void updateStatus(Long id, String status);
	
	void updateUserInfo(UserForm userForm, Account account, HttpSession session);

}
