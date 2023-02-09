package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.AppUser;
import shopbaeFood.model.Status;

public interface IAppUserRepository extends IGeneralRepository<AppUser> {

	@Override
	void save(AppUser appUser);

	@Override
	void update(AppUser appUser);

	@Override
	List<AppUser> findAll();

	@Override
	AppUser findById(Long id);

	List<AppUser> findAppUsersByStatus(Status status);

	AppUser findByName(String name);

}
