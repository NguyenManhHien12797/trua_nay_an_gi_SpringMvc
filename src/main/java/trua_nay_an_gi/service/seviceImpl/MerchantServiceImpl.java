package trua_nay_an_gi.service.seviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.repository.IAppUserRepository;
import trua_nay_an_gi.repository.IMerchantRepository;
import trua_nay_an_gi.service.IMerchantService;

@Service
@Transactional
public class MerchantServiceImpl implements IMerchantService {

	@Autowired
	private IMerchantRepository merchantRepository;

	@Autowired
	private IAppUserRepository userRepository;

	@Override
	public void save(Merchant merchant) {
		merchantRepository.save(merchant);
	}

	@Override
	public void update(Merchant merchant) {
		merchantRepository.update(merchant);
	}

	@Override
	public void delete(Long id) {
		Merchant merchant = this.findById(id);
		merchantRepository.delete(merchant);
	}

	@Override
	public List<Merchant> findAll() {
		return merchantRepository.findAll();
	}

	@Override
	public Merchant findById(Long id) {
		return merchantRepository.findById(id);
	}

	@Override
	public Merchant findByName(String name) {
		return merchantRepository.findByName(name);
	}

	@Override
	public void saveMerchantToRegister(String address, String avatar, String name, String phone, String status,
			Long account_id) {

		merchantRepository.saveMerchantToRegister(address, avatar, name, phone, status, account_id);
	}

	@Override
	public void updateStatus(Long id, String status, String role) {
		if ("merchant-list".equals(role)) {
			Merchant merchant = merchantRepository.findById(id);
			merchant.setStatus(status);
			merchantRepository.update(merchant);
		}
		if ("user-list".equals(role)) {
			AppUser appUser = userRepository.findById(id);
			appUser.setStatus(status);
			userRepository.update(appUser);
		}

	}

	@Override
	public List<?> findMerchantsOrUsersByStatus(String status, String role) {
		if ("merchant-list".equals(role)) {
			List<Merchant> merchants = merchantRepository.findAll();
			List<Merchant> merchants1 = null;
			if ("pending".equals(status)) {
				merchants1 = merchants.stream().filter(merchant -> "pending".equals(merchant.getStatus()))
						.collect(Collectors.toList());
			}
			if ("active".equals(status)) {
				merchants1 = merchants.stream().filter(merchant -> "active".equals(merchant.getStatus()))
						.collect(Collectors.toList());
			}
			if ("block".equals(status)) {
				merchants1 = merchants.stream().filter(merchant -> "block".equals(merchant.getStatus()))
						.collect(Collectors.toList());
			}

			return merchants1;
		}

		if ("user-list".equals(role)) {
			List<AppUser> users = userRepository.findAll();
			List<AppUser> users1 = null;
			if ("pending".equals(status)) {
				users1 = users.stream().filter(user -> "pending".equals(user.getStatus())).collect(Collectors.toList());
			}
			if ("active".equals(status)) {
				users1 = users.stream().filter(user -> "active".equals(user.getStatus())).collect(Collectors.toList());
			}
			if ("block".equals(status)) {
				users1 = users.stream().filter(user -> "block".equals(user.getStatus())).collect(Collectors.toList());
			}

			return users1;
		}

		return null;

	}

}
