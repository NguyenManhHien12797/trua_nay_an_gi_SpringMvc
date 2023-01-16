package trua_nay_an_gi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MerchantController {

	@GetMapping("/merchant")
	private String merchantPage() {
		return "merchant_page";
	}

	@GetMapping("merchant/{route}")
	private String merchantPage1(@PathVariable String route, Model model) {
		model.addAttribute("route", route);
		return "merchant_page";
	}
	
	@GetMapping("merchant/{route}/{status}")
	private String merchantPage2(@PathVariable String route, @PathVariable String status, Model model) {
		String rt = status;
		model.addAttribute("route", rt);
		model.addAttribute("status", status);
		return "merchant_page";
	}
}
