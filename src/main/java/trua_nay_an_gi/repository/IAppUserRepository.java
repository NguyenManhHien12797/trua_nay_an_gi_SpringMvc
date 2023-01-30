package trua_nay_an_gi.repository;

import java.util.List;

import trua_nay_an_gi.model.AppUser;

public interface IAppUserRepository extends IGeneralRepository<AppUser> {

	@Override
	void save(AppUser appUser);

	@Override
	void update(AppUser appUser);


	@Override
	List<AppUser> findAll();

	@Override
	AppUser findById(Long id);

	AppUser findByName(String name);

}
