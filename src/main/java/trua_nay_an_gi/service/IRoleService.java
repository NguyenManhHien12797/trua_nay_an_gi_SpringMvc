package trua_nay_an_gi.service;

import trua_nay_an_gi.model.AppRoles;

public interface IRoleService<AppRoles> {

	AppRoles findByName(String name);

	void setDefaultRole(Long accountId, Integer roleId);

}
