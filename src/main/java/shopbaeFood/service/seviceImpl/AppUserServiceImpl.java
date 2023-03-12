package shopbaeFood.service.seviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import shopbaeFood.model.Account;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.Status;
import shopbaeFood.model.UserForm;
import shopbaeFood.repository.IAccountRepository;
import shopbaeFood.repository.IAppUserRepository;
import shopbaeFood.service.IAppUserSevice;

@Service
@Transactional
public class AppUserServiceImpl implements IAppUserSevice {

	@Autowired
	private IAppUserRepository appUserRepository;

	@Autowired
	private IAccountRepository accountRepository;

	@Value("${file-upload}")
	private String fileUpload;

	@Override
	public void save(AppUser appUser) {
		appUserRepository.save(appUser);
	}

	@Override
	public void update(AppUser appUser) {
		appUserRepository.update(appUser);
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
	public void updateStatus(Long id, Status status) {
		AppUser appUser = this.findById(id);
		appUser.setStatus(status);
		appUserRepository.update(appUser);

	}

	@Override
	public void updateUserInfo(UserForm userForm, Account accountInput, HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account accountUpdate = accountRepository.findByName(authentication.getName());

		accountUpdate.setEmail(accountInput.getEmail());
		accountRepository.update(accountUpdate);
		AppUser user = appUserRepository.findById(userForm.getId());

		MultipartFile multipartFile = userForm.getAvatar();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(userForm.getAvatar().getBytes(), new File(fileUpload + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Chưa chọn file ảnh" + e.getMessage());
			fileName = user.getAvatar();
		}

		user.setName(userForm.getName());
		user.setPhone(userForm.getPhone());
		user.setAddress(userForm.getAddress());
		user.setAvatar(fileName);
		user.setAccount(accountUpdate);
		appUserRepository.update(user);

		session.setAttribute("user", accountUpdate);
		session.setAttribute("avatar", user.getAvatar());
		session.setAttribute("username", user.getName());

	}

}
