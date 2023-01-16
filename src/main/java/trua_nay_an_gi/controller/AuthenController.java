package trua_nay_an_gi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import trua_nay_an_gi.model.dto.AccountRegisterDTO;
import trua_nay_an_gi.service.IAuthenService;

@Controller
public class AuthenController {

	@Autowired
	private IAuthenService authenService;

	@GetMapping(value = { "/login" })
	public String showLoginForm() {
		return "login";

	}

	@GetMapping(value = { "/home" })
	public String home(Model model, HttpSession session) {
		String path = authenService.home(model, session);
		return path;
	}

	@GetMapping("/register/{role}")
	public ModelAndView showFormRegister(@PathVariable String role) {
		return authenService.showFormRegister(role);
	}

	@PostMapping("/register/{role}")
	public ModelAndView register(@PathVariable String role,
			@ModelAttribute("accountRegisterDTO") AccountRegisterDTO accountRegisterDTO) {

		authenService.register(accountRegisterDTO, role);
		ModelAndView modelAndView = new ModelAndView("/login");
		return modelAndView;
	}

}
