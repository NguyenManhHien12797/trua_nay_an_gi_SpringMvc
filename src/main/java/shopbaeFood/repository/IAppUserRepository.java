package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.AppUser;
import shopbaeFood.model.Status;

public interface IAppUserRepository extends IGeneralRepository<AppUser> {

	List<AppUser> findAppUsersByStatus(Status status);

	AppUser findByName(String name);

}
