package trua_nay_an_gi.service;

import java.util.List;

import trua_nay_an_gi.model.AppUser;

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

}
