package trua_nay_an_gi.service;

import trua_nay_an_gi.model.AccountRoleMap;

public interface IRoleService<AppRoles> {

	AppRoles findByName(String name);

	void setDefaultRole(AccountRoleMap accountRoleMap);

	trua_nay_an_gi.model.AppRoles findById(Long id);
}
