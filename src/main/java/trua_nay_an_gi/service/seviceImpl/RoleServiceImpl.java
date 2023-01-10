package trua_nay_an_gi.service.seviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.AppRoles;
import trua_nay_an_gi.repository.IRoleRepository;
import trua_nay_an_gi.service.IRoleService;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService<AppRoles> {

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public void setDefaultRole(Long accountId, Integer roleId) {
		roleRepository.setDefaultRole(accountId, roleId);
	}

	@Override
	public AppRoles findByName(String name) {
		return roleRepository.findByName(name);
	}

}
