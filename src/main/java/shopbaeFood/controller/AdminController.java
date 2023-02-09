package shopbaeFood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shopbaeFood.model.Status;
import shopbaeFood.service.IMerchantService;

@Controller
public class AdminController {

	@Autowired
	private IMerchantService merchantService;

	// Show trang admin theo role: user/ merchant và status: pending/ active/ block
	@GetMapping(value = { "/admin/{role}/{status}" })
	public String adminPage(@PathVariable String role, @PathVariable Status status, Model model) {
		addListAttribute(status, role, model);

		return "admin_page";
	}

	// Update status
	@PostMapping("/admin/{role}/{stt}/{status}/{id}")
	public String updateStatus(@PathVariable String role, @PathVariable Long id, @PathVariable Status status,
			@PathVariable Status stt, Model model) {
		merchantService.updateStatus(id, status, role);
		addListAttribute(stt, role, model);
		return "fragments/app-fragments ::${stt}";
	}

	private void addListAttribute(Status stt, String role, Model model) {
		String navTitle = "Kênh Admin";

		List<?> usersOrMechants = merchantService.findMerchantsOrUsersByStatus(stt, role);
		model.addAttribute("usersOrMechants", usersOrMechants);
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("role", role);
		model.addAttribute("stt", stt);
	}

}
