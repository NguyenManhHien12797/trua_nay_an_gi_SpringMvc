package shopbaeFood.service.seviceImpl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import shopbaeFood.exception.CheckOtpException;
import shopbaeFood.model.Account;
import shopbaeFood.model.AccountRoleMap;
import shopbaeFood.model.AppRoles;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.Mail;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Product;
import shopbaeFood.model.Status;
import shopbaeFood.model.dto.AccountRegisterDTO;
import shopbaeFood.model.dto.PasswordDTO;
import shopbaeFood.repository.IAccountRepository;
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.IAppUserSevice;
import shopbaeFood.service.IAuthenService;
import shopbaeFood.service.IMailService;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.service.IProductService;
import shopbaeFood.service.IRoleService;
import shopbaeFood.util.Constants;

@Service
@Transactional
public class AuthenServiceImpl implements IAuthenService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAppUserSevice userSevice;

    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IMailService mailService;

    public static final String USER = "user";
    public static final String MECHANT = "merchant";
    public static final long ROLE_USER_ID = 1L;
    public static final long ROLE_MECHANT_ID = 3L;
    public static final String MAIL_SUBJECT = "Mã xác nhận OTP";
    public static final String MAIL_FROM = "nguyenhien81f@gmail.com";

    @Override
    public void register(AccountRegisterDTO accountRegisterDTO, String role) {
        Status status = Status.PENDING;
        boolean isEnabled = true;
        boolean firstLogin = true;
        String pass = passwordEncoder.encode(accountRegisterDTO.getPassword());
        Account account = new Account(accountRegisterDTO.getUserName(), pass, isEnabled, firstLogin,
                accountRegisterDTO.getEmail());
        accountService.save(account);

        String avatar = "images.jpg";

        if (USER.equals(role)) {
            AppRoles appRole = roleService.findById(ROLE_USER_ID);
            roleService.setDefaultRole(new AccountRoleMap(account, appRole));
            userSevice.save(new AppUser(accountRegisterDTO.getAddress(), avatar, accountRegisterDTO.getName(),
                    accountRegisterDTO.getPhone(), status, account));
        }
        if (MECHANT.equals(role)) {
            AppRoles appRole = roleService.findById(ROLE_MECHANT_ID);
            roleService.setDefaultRole(new AccountRoleMap(account, appRole));
            merchantService.save(new Merchant(accountRegisterDTO.getAddress(), avatar, accountRegisterDTO.getName(),
                    accountRegisterDTO.getPhone(), status, accountRegisterDTO.getCategory(), account));
        }

    }

    @Override
    public void createOtp() {
        Account account = accountService.getAccount();
        if(account.getOtp() != null){
            return;
        }
        double randomDouble = Math.random();
        randomDouble = randomDouble * 1000000 + 1;
        int OTP = (int) randomDouble;
        account.setOtp(String.valueOf(OTP));
        accountRepository.update(account);

        Mail mail = new Mail();
        mail.setMailTo(account.getEmail());
        mail.setMailFrom(MAIL_FROM);
        mail.setMailSubject(MAIL_SUBJECT);
        String content = MessageFormat.format("Mã OTP của bạn là: {0} \nVui lòng không chia sẻ với ai",
                String.valueOf(OTP));
        mail.setMailContent(content);
        mailService.sendEmail(mail);

    }

    @Override
    public String checkOtp(Long account_id, String otp) {
        Account account = accountRepository.findById(account_id);
        if (otp.equals(account.getOtp())) {
            return "ok";
        }
        throw new CheckOtpException(500, Constants.RESPONSE_MESSAGE.WRONG_OTP);
    }

    @Override
    public void changePass(String pass, Long account_id) {
        Account account = accountRepository.findById(account_id);

        account.setPassword(passwordEncoder.encode(pass));
        account.setOtp(null);
        accountRepository.update(account);
    }

    @Override
    public String showMessageLogin(String mess) {
        String message = " ";
        if (Constants.LOGIN_STATE.NOT_LOGIN.equals(mess)) {
            message = Constants.RESPONSE_MESSAGE.NOT_LOGIN;
        }
        if (Constants.LOGIN_STATE.TIME_OUT.equals(mess)) {
            message = Constants.RESPONSE_MESSAGE.TIME_OUT;
        }
        if (Constants.LOGIN_STATE.BAN_ACCOUNT.equals(mess)) {
            message = Constants.RESPONSE_MESSAGE.LOGIN_FAILE_ACCOUNT_BLOCK;
        }

        return message;
    }

    @Override
    public boolean changePass(PasswordDTO passwordDTO) {
        Account account = accountService.getAccount();
        if (passwordEncoder.matches(passwordDTO.getCurrentPassword(), account.getPassword())) {
            if (passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
                account.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
                account.setOtp(null);
                account.setFirstLogin(false);
                accountRepository.update(account);

                return true;
            }
        }

        return false;
    }

    @Override
    public List<Merchant> getMerchants(String address, String category, String quickSearch, HttpSession session) {
        Account account = accountService.getAccount();
        if (address == null) {
            if (session.getAttribute("address") != null) {
                address = (String) session.getAttribute("address");
            } else {
                if (account != null) {
                    if (account.getUser() != null) {
                        address = accountService.getAccount().getUser().getAddress();
                    }

                    if (account.getMerchant() != null) {
                        address = accountService.getAccount().getMerchant().getAddress();
                    }
                } else {
                    address = "Hà Nội";
                }
            }

        } else {
            session.setAttribute("address", address);
            getAddress().remove(address);
        }
        if (category == null) {
            if (session.getAttribute("category") != null) {
                category = (String) session.getAttribute("category");
            } else {
                category = "Đồ ăn";
            }

        } else {
            session.setAttribute("category", category);
        }
        List<Merchant> merchants = new ArrayList<>();
        if ("all".equals(quickSearch) || quickSearch == null) {
            merchants = merchantService.findMerchantsByStatusAndAddressAndCategory(Status.ACTIVE, address, category);
        } else {
            Map<String, String> quickSearchs = getListQuickSearch(category);
            merchants = merchantService.findMerchantsByStatusAndAddressAndCategoryAndProducName(Status.ACTIVE, address,
                    category, quickSearchs.get(quickSearch));
        }

        return merchants;
    }

    public List<String> getAddress() {
        List<String> listAddress = new ArrayList<String>();
        listAddress.add("Hà Nội");
        listAddress.add("Tp.HCM");
        listAddress.add("Đà Nẵng");
        return listAddress;
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<String>();
        categories.add("Đồ ăn");
        categories.add("Thực phẩm");
        categories.add("Bia");
        categories.add("Hoa");
        categories.add("Thuốc");
        return categories;
    }

    public Map<String, String> getListQuickSearch(String category) {
        Map<String, String> quickSearchs = new HashMap<String, String>();
        if ("Đồ ăn".equals(category)) {
            quickSearchs.put("all", "All");
            quickSearchs.put("bun", "Bún");
            quickSearchs.put("pho", "Phở");
            quickSearchs.put("hamburger", "Hamburger");
            quickSearchs.put("do-chay", "Đồ chay");
            quickSearchs.put("do-uong", "Đồ uống");
            quickSearchs.put("trang-mieng", "Tráng miệng");
        }
        if ("Thực phẩm".equals(category)) {
            quickSearchs.put("all", "All");
            quickSearchs.put("thit", "Thịt");
            quickSearchs.put("thit-bo", "Thịt bò");
            quickSearchs.put("thit-cho", "Thịt chó");
            quickSearchs.put("thit-ga", "Thịt gà");
        }
        return quickSearchs;
    }

}
