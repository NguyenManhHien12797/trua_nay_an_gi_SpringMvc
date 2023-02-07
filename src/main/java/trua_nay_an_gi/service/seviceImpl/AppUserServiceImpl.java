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
import trua_nay_an_gi.model.UserForm;
import trua_nay_an_gi.repository.IAccountRepository;
import trua_nay_an_gi.repository.IAppUserRepository;
import trua_nay_an_gi.service.IAppUserSevice;

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
	public void updateStatus(Long id, String status) {
		AppUser appUser = this.findById(id);
		appUser.setStatus(status);
		appUserRepository.update(appUser);

	}

	@Override
	public void updateUserInfo(UserForm userForm, Account account, HttpSession session) {
		Account account1 = (Account) session.getAttribute("user");
		
		account1.setEmail(account.getEmail());
		accountRepository.update(account1);
		AppUser user = appUserRepository.findById(userForm.getId());
		
		MultipartFile multipartFile = userForm.getAvatar();
		String fileName =multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(userForm.getAvatar().getBytes(), new File(fileUpload + fileName));
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("Chưa chọn file ảnh"+e.getMessage());
			fileName= user.getAvatar();
		}
		
		
		user.setName(userForm.getName());
		user.setPhone(userForm.getPhone());
		user.setAddress(userForm.getAddress());
		user.setAvatar(fileName);
		user.setAccount(account1);
		appUserRepository.update(user);
		
		session.setAttribute("user", account1);
		session.setAttribute("avatar", user.getAvatar());
		session.setAttribute("username", user.getName());
		
	}

}
