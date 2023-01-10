package trua_nay_an_gi.repository;
import trua_nay_an_gi.model.AppRoles;

public interface IRoleRepository<AppRoles>{

	 trua_nay_an_gi.model.AppRoles findByName(String name);
	 
	 void setDefaultRole(Long accountId, Integer roleId);

}
