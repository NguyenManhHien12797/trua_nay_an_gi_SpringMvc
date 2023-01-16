package trua_nay_an_gi.service;

public interface IRoleService<AppRoles> {

	AppRoles findByName(String name);

	void setDefaultRole(Long accountId, Integer roleId);

}
