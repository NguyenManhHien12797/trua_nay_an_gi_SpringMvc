package trua_nay_an_gi.service.seviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.repository.IAppUserRepository;
import trua_nay_an_gi.service.IAppUserSevice;

@Service
@Transactional
public class AppUserServiceImpl implements IAppUserSevice {

	@Autowired
	private IAppUserRepository appUserRepository;

	@Override
	public void save(AppUser appUser) {
		appUserRepository.save(appUser);
	}

	@Override
	public void update(AppUser appUser) {
		appUserRepository.update(appUser);
	}

	@Override
	public void delete(Long id) {
		AppUser appUser = this.findById(id);
		appUserRepository.delete(appUser);
	}

	@Override
	public List<AppUser> findAll() {
		return appUserRepository.findAll();
	}

	@Override
	public AppUser findById(Long id) {
		return appUserRepository.findById(id);
	}

	@Override
	public AppUser findByName(String name) {
		return appUserRepository.findByName(name);
	}

	@Override
	public void saveUserToRegister(String address, String avatar, String name, String phone, String status,
			Long account_id) {
		appUserRepository.saveUserToRegister(address, avatar, name, phone, status, account_id);
	}

	@Override
	public void updateStatus(Long id, String status) {
		AppUser appUser = this.findById(id);
		appUser.setStatus(status);
		appUserRepository.update(appUser);

	}

}
