package trua_nay_an_gi.repository;

import trua_nay_an_gi.model.AccountRoleMap;

public interface IRoleRepository<AppRoles> {

	trua_nay_an_gi.model.AppRoles findByName(String name);

	void setDefaultRole(AccountRoleMap accountRoleMap);
	
	trua_nay_an_gi.model.AppRoles findById(Long id);

}
