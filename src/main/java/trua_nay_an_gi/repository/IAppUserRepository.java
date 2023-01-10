package trua_nay_an_gi.repository;

import java.util.List;

import trua_nay_an_gi.model.AppUser;

public interface IAppUserRepository extends IGeneralRepository<AppUser> {

	@Override
	void save(AppUser appUser);

	@Override
	void update(AppUser appUser);

	@Override
	void delete(AppUser appUser);

	@Override
	List<AppUser> findAll();

	@Override
	AppUser findById(Long id);

	AppUser findByName(String name);

	void saveUserToRegister(String address, String avatar, String name, String phone,String status, Long account_id);

}
