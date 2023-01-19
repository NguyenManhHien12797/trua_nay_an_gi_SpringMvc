package trua_nay_an_gi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import trua_nay_an_gi.service.IMerchantService;

@Controller
public class AdminController {

	@Autowired
	private IMerchantService merchantService;

	@GetMapping(value = { "/admin/{role}/{status}" })
	public String adminPage(@PathVariable String role, @PathVariable String status, Model model) {
		addListAttribute(status, role, model);
	
		return "admin_page";
	}

	@PostMapping("/admin/{role}/{stt}/{status}/{id}")
	public String updateStatus(@PathVariable String role, @PathVariable Long id, @PathVariable String status,
			@PathVariable String stt, Model model) {
		merchantService.updateStatus(id, status, role);
		addListAttribute(stt, role, model);
		return "fragments/app-fragments ::" + stt;
	}

	private void addListAttribute(String stt, String role, Model model) {
		String navTitle = "Kênh Admin";
		
		List<?> usersOrMechants = merchantService.findMerchantsOrUsersByStatus(stt, role);
		model.addAttribute("usersOrMechants", usersOrMechants);
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("role", role);
		model.addAttribute("stt", stt);
	}

}
