package trua_nay_an_gi.service;

import java.util.List;

import trua_nay_an_gi.model.AppUser;

public interface IAppUserSevice extends IGeneralService<AppUser> {

	@Override
	void save(AppUser appUser);

	@Override
	void update(AppUser appUser);

	@Override
	void delete(Long id);

	@Override
	List<AppUser> findAll();

	@Override
	AppUser findById(Long id);

	AppUser findByName(String name);

	void saveUserToRegister(String address, String avatar, String name, String phone, String status, Long account_id);

}
