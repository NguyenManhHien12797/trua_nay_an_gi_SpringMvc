package trua_nay_an_gi.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import trua_nay_an_gi.model.dto.AccountRegisterDTO;

public interface IAuthenService {

	void register(AccountRegisterDTO accountRegisterDTO, String role);

	boolean isAdmin(HttpSession session);

	String home(Model model, HttpSession session);
	
	String showLoginForm(String mess);

	ModelAndView showFormRegister(String role);

	void checkLogin(Model model,HttpSession session);
	
	void createOtp(HttpSession session);
	
	void changePass(String pass, Long account_id);
	
	String checkOtp(Long account_id,String otp);
	
	String merchantDetails(Long id,Model model, HttpSession session );
}
