package shopbaeFood.service;

import shopbaeFood.model.AccountRoleMap;
import shopbaeFood.model.AppRoles;

public interface IRoleService {

	AppRoles findByName(String name);

	void setDefaultRole(AccountRoleMap accountRoleMap);

	AppRoles findById(Long id);
}
