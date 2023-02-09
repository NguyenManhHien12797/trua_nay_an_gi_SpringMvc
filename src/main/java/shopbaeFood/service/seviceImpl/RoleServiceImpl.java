package shopbaeFood.service.seviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shopbaeFood.model.AccountRoleMap;
import shopbaeFood.model.AppRoles;
import shopbaeFood.repository.IRoleRepository;
import shopbaeFood.service.IRoleService;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public void setDefaultRole(AccountRoleMap accountRoleMap) {
		roleRepository.setDefaultRole(accountRoleMap);
	}

	@Override
	public AppRoles findByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public AppRoles findById(Long id) {
		return roleRepository.findById(id);
	}

}
