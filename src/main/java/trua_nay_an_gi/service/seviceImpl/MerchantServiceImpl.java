package trua_nay_an_gi.service.seviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.repository.IMerchantRepository;
import trua_nay_an_gi.service.IMerchantService;

@Service
@Transactional
public class MerchantServiceImpl implements IMerchantService {

	@Autowired
	private IMerchantRepository merchantRepository;

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

}
