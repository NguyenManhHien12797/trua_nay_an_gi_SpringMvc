package shopbaeFood.service.seviceImpl;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import shopbaeFood.model.Account;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.Mail;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.MerchantForm;
import shopbaeFood.model.Status;
import shopbaeFood.repository.IAccountRepository;
import shopbaeFood.repository.IAppUserRepository;
import shopbaeFood.repository.IMerchantRepository;
import shopbaeFood.service.IMailService;
import shopbaeFood.service.IMerchantService;

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
	
	public static final String ROUTE_MERCHANT = "merchant-list";
	public static final String ROUTE_USER = "user-list";
	public static final String MAIL_FROM = "nguyenhuuquyet07092001@gmail.com";
	public static final String MAIL_ACCEPT_SUBJECT = "Mail xác nhận đăng ký";
	public static final String MAIL_REFUSE_SUBJECT = "Mail từ chối đăng ký làm người bán";

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
	public void updateStatus(Long id, Status status, String navRoute) {
		if (ROUTE_MERCHANT.equals(navRoute)) {
			Merchant merchant = merchantRepository.findById(id);
			Account account = accountRepository.findById(merchant.getAccount().getId());
			merchant.setStatus(status);
			System.out.println("--------------------Update-----------------");
			merchantRepository.update(merchant);
			System.out.println("--------------------Update-----------------");
			if(Status.PENDING.equals(status)) {
				Mail mail = new Mail();
				mail.setMailFrom(MAIL_FROM);
				mail.setMailTo(account.getEmail());
				String subject = " ";
				String content = " ";
				if (Status.ACTIVE.equals(status)) {
					subject = MAIL_ACCEPT_SUBJECT;
					content = MessageFormat.format("Dear {0} !\n Cảm ơn bạn đã đăng ký làm thành viên của hệ thống",
							merchant.getName());
				}
				if (Status.REFUSE.equals(status)) {
					subject = MAIL_REFUSE_SUBJECT;
					content = MessageFormat.format(
							"Dear {0} !\\n Cảm ơn bạn đã đăng ký làm merchant của hệ thống. Chúng tôi rất tiếc khi bạn chưa đủ điều kiện để đăng ký làm người bán.",
							merchant.getName());
				}
				mail.setMailSubject(subject);
				mail.setMailContent(content);

				mailService.sendEmail(mail);
			}
		}
		if (ROUTE_USER.equals(navRoute)) {
			AppUser appUser = userRepository.findById(id);
			appUser.setStatus(status);
			userRepository.update(appUser);
		}

	}

	@Override
	public List<?> findMerchantsOrUsersByStatus(Status status, String route) {
		if (ROUTE_MERCHANT.equals(route)) {
			List<Merchant> merchants = merchantRepository.findMerchantsByStatus(status);
			return merchants;
		}
		if (ROUTE_USER.equals(route)) {
			List<AppUser> users = userRepository.findAppUsersByStatus(status);
			return users;
		}
		return null;

	}

	@Override
	public void updateMerchantInfo(MerchantForm merchantForm, Account accountInput, HttpSession session) {
		Account accountUpdate = (Account) session.getAttribute("user");
		accountUpdate.setEmail(accountInput.getEmail());
		accountRepository.update(accountUpdate);

		Merchant merchantUpdate = merchantRepository.findById(merchantForm.getId());
		MultipartFile multipartFile = merchantForm.getAvatar();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(merchantForm.getAvatar().getBytes(), new File(fileUpload + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Chưa chọn file ảnh: " + e.getMessage());
			fileName = merchantUpdate.getAvatar();
		}

		merchantUpdate.setName(merchantForm.getName());
		merchantUpdate.setPhone(merchantForm.getPhone());
		merchantUpdate.setOpenTime(merchantForm.getOpenTime());
		merchantUpdate.setCloseTime(merchantForm.getCloseTime());
		merchantUpdate.setAvatar(fileName);
		merchantRepository.update(merchantUpdate);

		session.setAttribute("avatar", merchantUpdate.getAvatar());
		session.setAttribute("username", merchantUpdate.getName());

	}

	@Override
	public List<Merchant> findMerchantsByStatus(Status status) {
		return merchantRepository.findMerchantsByStatus(status);
	}

}
