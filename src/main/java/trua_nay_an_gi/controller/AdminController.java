package trua_nay_an_gi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.service.IAppUserSevice;
import trua_nay_an_gi.service.IMerchantService;

@Controller
public class AdminController {
	
	@Autowired
	private IMerchantService merchantService;

	
	@GetMapping(value = { "/admin/{role}/{status}" }) 
	public String adminPage1(@PathVariable String role,@PathVariable String status,Model model) {
		
		  List<?>usersOrMechants = merchantService.findMerchantsOrUsersByStatus(status, role);
		  model.addAttribute("usersOrMechants", usersOrMechants);
		  model.addAttribute("role", role);
		  model.addAttribute("stt", status); 
	  
		  return "admin_page";
	  }  
	  
	@PostMapping("/admin/{role}/{status}/{id}")
	public String doUpdateAppUser(@PathVariable String role, @PathVariable Long id, @PathVariable String status) {
		merchantService.updateStatus(id, status,role);
		return "admin_page";
	}  
	  
}
