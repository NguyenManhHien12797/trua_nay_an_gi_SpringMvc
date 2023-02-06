package trua_nay_an_gi.service.seviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.Mail;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.model.MerchantForm;
import trua_nay_an_gi.repository.IAccountRepository;
import trua_nay_an_gi.repository.IAppUserRepository;
import trua_nay_an_gi.repository.IMerchantRepository;
import trua_nay_an_gi.service.IMailService;
import trua_nay_an_gi.service.IMerchantService;

@Service
@Transactional
public class MerchantServiceImpl implements IMerchantService {
	
	@Value("${file-upload}")
	private String fileUpload;

	@Autowired
	private IMerchantRepository merchantRepository;

	@Autowired
	private IAppUserRepository userRepository;
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private IMailService mailService;

	@Override
	public void save(Merchant merchant) {
		merchantRepository.save(merchant);
	}

	@Override
	public void update(Merchant merchant) {
		merchantRepository.update(merchant);
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
	public void updateStatus(Long id, String status, String role) {
		if ("merchant-list".equals(role)) {
			Merchant merchant = merchantRepository.findById(id);
			Account account = accountRepository.findById(merchant.getAccount().getId());
			merchant.setStatus(status);
			merchantRepository.update(merchant);
			   Mail mail = new Mail();
		        mail.setMailFrom("nguyenhuuquyet07092001@gmail.com");
		        mail.setMailTo(account.getEmail());
		        String subject= " ";
		        String content = " ";
		        if("active".equals(status)) {
		        	subject = "Mail xác nhận đăng ký";
		        	content = "Dear "+merchant.getName()+"!\n Cảm ơn bạn đã đăng ký làm thành viên của hệ thống";
		        }
		        if("refuse".equals(status)) {
		        	subject = "Mail từ chối đăng ký làm người bán";
		        	content = "Dear "+merchant.getName()+"!\n Cảm ơn bạn đã đăng ký làm merchant của hệ thống. Chúng tôi rất tiếc khi bạn chưa đủ điều kiện để đăng ký làm người bán.";
		        }
		        mail.setMailSubject(subject);
		        mail.setMailContent(content);

			mailService.sendEmail(mail);
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
			List<Merchant> merchants = merchantRepository.findMerchantsByStatus(status);
			return merchants;
		}
		if ("user-list".equals(role)) {
			List<AppUser> users = userRepository.findAppUsersByStatus(status);
			return users;
		}
		return null;

	}

	@Override
	public void updateMerchantInfo(MerchantForm merchantForm, Account account, HttpSession session) {
		Account account1 = (Account) session.getAttribute("user");
		account1.setEmail(account.getEmail());
		accountRepository.update(account1);
		
		
		MultipartFile multipartFile = merchantForm.getAvatar();
		String fileName =multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(merchantForm.getAvatar().getBytes(), new File(fileUpload + fileName));
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.print("Chưa chọn file ảnh"+e.getMessage());
			fileName= account1.getMerchant().getAvatar();
		}
	
		Merchant merchant = merchantRepository.findById(merchantForm.getId());
		merchant.setName(merchantForm.getName());
		merchant.setPhone(merchantForm.getPhone());
		merchant.setOpenTime(merchantForm.getOpenTime());
		merchant.setCloseTime(merchantForm.getCloseTime());
		merchant.setAvatar(fileName);
		merchantRepository.update(merchant);
		
		session.setAttribute("avatar", merchant.getAvatar());
		session.setAttribute("username", merchant.getName());
		
	}

}
