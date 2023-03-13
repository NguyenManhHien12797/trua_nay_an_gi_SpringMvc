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
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.IMailService;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.util.Page;

@Service
@Transactional
public class MerchantServiceImpl implements IMerchantService {

	@Value("${file-upload}")
	private String fileUpload;

	private final int PAGE_SIZE = 5;

	@Autowired
	private IMerchantRepository merchantRepository;

	@Autowired
	private IAppUserRepository userRepository;

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IMailService mailService;

	public static final String NAV_ROUTE_MERCHANT = "merchant-list";
	public static final String NAV_ROUTE_USER = "user-list";
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
		if (NAV_ROUTE_MERCHANT.equals(navRoute)) {
			Merchant merchant = merchantRepository.findById(id);
			Account account = accountRepository.findById(merchant.getAccount().getId());
			merchant.setStatus(status);
			merchantRepository.update(merchant);
			if (Status.PENDING.equals(status)) {
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
		if (NAV_ROUTE_USER.equals(navRoute)) {
			AppUser appUser = userRepository.findById(id);
			appUser.setStatus(status);
			userRepository.update(appUser);
		}

	}

	@Override
	public List<?> findMerchantsOrUsersByStatus(Status status, String navRoute) {
		if (NAV_ROUTE_MERCHANT.equals(navRoute)) {
			List<Merchant> merchants = merchantRepository.findMerchantsByStatus(status);
			return merchants;
		}
		if (NAV_ROUTE_USER.equals(navRoute)) {
			List<AppUser> users = userRepository.findAppUsersByStatus(status);
			return users;
		}
		return null;

	}

	@Override
	public Page<?> page(Status status, String navRoute, int pageNumber) {

		int lastPageNumber = 0;
		if (NAV_ROUTE_MERCHANT.equals(navRoute)) {
			Page<Merchant> page = new Page<Merchant>();
			List<Merchant> merchants = page.paging(pageNumber, PAGE_SIZE,
					merchantRepository.findMerchantsByStatus(status));
			lastPageNumber = page.lastPageNumber(PAGE_SIZE, merchantRepository.findMerchantsByStatus(status));
			page.setPaging(merchants);
			page.setLastPageNumber(lastPageNumber);
			return page;
		}
		if (NAV_ROUTE_USER.equals(navRoute)) {
			Page<AppUser> page = new Page<AppUser>();
			List<AppUser> users = page.paging(pageNumber, PAGE_SIZE, userRepository.findAppUsersByStatus(status));
			lastPageNumber = page.lastPageNumber(PAGE_SIZE, userRepository.findAppUsersByStatus(status));
			page.setPaging(users);
			page.setLastPageNumber(lastPageNumber);
			return page;
		}

		return null;
	}

	@Override
	public void updateMerchantInfo(MerchantForm merchantForm, Account accountInput, HttpSession session) {
		Account accountUpdate = accountService.getAccount();
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

	@Override
	public List<Merchant> findMerchantsByStatusAndAddressAndCategory(Status status, String address, String category) {
		return merchantRepository.findMerchantsByStatusAndAddressAndCategory(status, address, category);
	}

	@Override
	public List<Merchant> findMerchantsByStatusAndCategoryAndSearch(Status status, String category, String search) {
		return merchantRepository.findMerchantsByStatusAndCategoryAndSearch(status, category, search);
	}

	@Override
	public List<Merchant> findMerchantsByStatusAndAddressAndCategoryAndProducName(Status status, String address,
			String category, String quickSearch) {
		return merchantRepository.findMerchantsByStatusAndAddressAndCategoryAndProducName(status, address, category, quickSearch);
	}

}
