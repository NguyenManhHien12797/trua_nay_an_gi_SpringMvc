package shopbaeFood.repository;

import shopbaeFood.model.AccountRoleMap;
import shopbaeFood.model.AppRoles;

public interface IRoleRepository {

	AppRoles findByName(String name);

	void setDefaultRole(AccountRoleMap accountRoleMap);

	AppRoles findById(Long id);

}
