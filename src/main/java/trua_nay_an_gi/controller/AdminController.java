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

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.service.IAppUserSevice;
import trua_nay_an_gi.service.IMerchantService;

@Controller
public class AdminController {
	
	@Autowired
	private IAppUserSevice userSevice;
	
	@Autowired
	private IMerchantService merchantService;

	@GetMapping(value = { "/admin" })
	public String showAdmin() {
		return "products";
	}

	@GetMapping(value = { "/admin/products" })
	public String showProduct() {
		return "products";
	}
	
	@GetMapping(value = { "/admin/home" })
	public String showHome() {
		return "home";
	}

	@GetMapping(value = { "/admin/about" })
	public String showAbout() {
		return "about";
	}

	
	  @GetMapping(value = { "/admin/merchant-list/{status}" }) 
	  public String adminPage1(@PathVariable String status,Model model) {
		  String stt = "";
	
	  if("pending".equals(status)) {
		  stt = "pending";
		  
	  }
	  if("active".equals(status)) {
		  stt = "active";
		  
	  }
	  if("block".equals(status)) {
		  stt = "block";
		  
	  }

	  String role ="merchant-list";
	  model.addAttribute("role", role);
	  model.addAttribute("stt", stt); 
	  
	  List<Merchant> merchants = merchantService.findAll();
	  List<Merchant>  merchantPending = merchants.stream()
			  .filter(merchant -> "pending".equals(merchant.getStatus()))
			  .collect(Collectors.toList());
	  List<Merchant>  merchantActive = merchants.stream()
			  .filter(user -> "active".equals(user.getStatus()))
			  .collect(Collectors.toList());
	  List<Merchant>  merchantBlock = merchants.stream()
			  .filter(merchant -> "block".equals(merchant.getStatus()))
			  .collect(Collectors.toList());
	  model.addAttribute("merchantPending", merchantPending);
	  model.addAttribute("merchantActive", merchantActive);
	  model.addAttribute("merchantBlock", merchantBlock);
	  
	  return "admin_page";
	  }
	  
	  @GetMapping(value = { "/admin/user-list/{status}" }) 
	  public String adminPage3(@PathVariable String status, Model model) {
		  String stt = "";
		
		 
		  if("pending".equals(status)) {
			  stt = "pending";
		  }
		  if("active".equals(status)) {
			  stt = "active";
		  }
		  if("block".equals(status)) {
			  stt = "block";
			  
		  }
	  String role ="user-list";
	  model.addAttribute("role", role);
	  model.addAttribute("stt", stt); 
	  List<AppUser> users = userSevice.findAll();
	  List<AppUser>  userPending = users.stream()
			  .filter(user -> "pending".equals(user.getStatus()))
			  .collect(Collectors.toList());
	  List<AppUser>  userActive = users.stream()
			  .filter(user -> "active".equals(user.getStatus()))
			  .collect(Collectors.toList());
	  List<AppUser>  userBlock = users.stream()
			  .filter(user -> "block".equals(user.getStatus()))
			  .collect(Collectors.toList());
	  model.addAttribute("userPending", userPending);
	  model.addAttribute("userActive", userActive);
	  model.addAttribute("userBlock", userBlock);
	
	  return "admin_page";
	  }
	  
	  
	  @GetMapping(value = { "/admin/{role}" }) 
	  public String adminPage2(@PathVariable String role, Model model) {
	  	  String stt = "";
	  if("admin-page".equals(role)) {
		  role ="merchant-list";
	  	  stt = "active";
	  	 
	  }
	  if("merchant-list".equals(role)) {
		  role ="merchant-list";
	  	  stt = "active";
	  	  
	  }
	  if("user-list".equals(role)) {
		  role ="user-list";
	  	  stt = "active";
	  	  
	  }
	  model.addAttribute("role", role);
	  model.addAttribute("stt", stt); 
	  return "admin_page"; 
	  }

	  
		@PostMapping("/admin/merchant-list/pending")
		public String doUpdateCustomer(@ModelAttribute("appUser") AppUser appUser, Model model) {
			userSevice.update(appUser);
			model.addAttribute("userPending", userSevice.findAll());
			return "admin_page";
		}
	  
}
