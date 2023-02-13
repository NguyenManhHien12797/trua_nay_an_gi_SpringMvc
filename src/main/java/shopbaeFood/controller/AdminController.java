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

	/**
	 * This method return the admin page
	 * @param navRoute : merchant-list/ user-list
	 * @param status : PENDING/ ACTIVE/ BLOCK
	 * @param model
	 * @return addmin_page
	 */
	@GetMapping(value = { "/admin/{navRoute}/{status}" })
	public String adminPage(@PathVariable String navRoute, @PathVariable Status status, Model model) {
		addListAttribute(status, navRoute, model);

		return "admin_page";
	}

	/**
	 * This method is used to update status and return fragments by route
	 * @param navRoute : merchant-list/ user-list
	 * @param id
	 * @param status : PENDING/ ACTIVE/ BLOCK
	 * @param route : PENDING/ ACTIVE/ BLOCK
	 * @param model
	 * @return fragments/app-fragments: PENDING/ ACTIVE/ BLOCK
	 */
	@PostMapping("/admin/{navRoute}/{route}/{status}/{id}")
	public String updateStatus(@PathVariable String navRoute, @PathVariable Long id, @PathVariable Status status,
			@PathVariable Status route, Model model) {
		merchantService.updateStatus(id, status, navRoute);
		addListAttribute(route, navRoute, model);
		return "fragments/app-fragments ::${route}";
	}
	
	/**
	 * This method is used to add Attribute 
	 * @param status
	 * @param navRoute
	 * @param model
	 */

	private void addListAttribute(Status status, String navRoute, Model model) {
		String navTitle = "Kênh Admin";

		List<?> usersOrMechants = merchantService.findMerchantsOrUsersByStatus(status, navRoute);
		model.addAttribute("usersOrMechants", usersOrMechants);
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("navRoute", navRoute);
		model.addAttribute("status", status);
		model.addAttribute("role", "admin");
	}

}
