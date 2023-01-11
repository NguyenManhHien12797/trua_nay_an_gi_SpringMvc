package trua_nay_an_gi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping(value = { "/admin" })
	public String showAdmin() {
		return "products";
	}

	@GetMapping(value = { "/admin/products" })
	public String showProduct() {
		return "products";
	}

	@GetMapping(value = { "/admin/about" })
	public String showAbout() {
		return "about";
	}

	@GetMapping(value = { "/admin/admin-page" })
	public String adminPage() {
		return "admin_page";
	}
	
	@GetMapping(value = { "/admin/merchant-list" })
	public String listMerchant() {
		return "admin_page";
	}
	
	@GetMapping(value = { "/admin/user-list" })
	public String listUser() {
		return "user_list";
	}

}
