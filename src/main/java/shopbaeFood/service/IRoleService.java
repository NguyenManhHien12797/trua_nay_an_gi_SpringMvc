package shopbaeFood.service;

import shopbaeFood.model.AccountRoleMap;
import shopbaeFood.model.AppRoles;

public interface IRoleService {

	/**
	 * This method is use to find AppRoles by name
	 * @param name
	 * @return AppRoles
	 */
	AppRoles findByName(String name);

	/**
	 * This method is used to set role
	 * @param accountRoleMap
	 */
	void setDefaultRole(AccountRoleMap accountRoleMap);
	
	/**
	 * This method is used to find AppRoles by id
	 * @param id
	 * @return AppRoles
	 */
	AppRoles findById(Long id);
}
