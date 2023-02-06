package trua_nay_an_gi.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import trua_nay_an_gi.model.dto.CartDTO;
import trua_nay_an_gi.service.ICartService;

@Controller
public class CartController {
	
	@Autowired
	private ICartService cartService;

	@PostMapping("/user/addToCart")
	private String addToCart(@RequestBody CartDTO cartDTO, HttpSession session) {
		
		return cartService.addToCart(cartDTO, session);
	}
	
	
	@GetMapping("/user/cart")
	private String showCart(Model model, HttpSession session) {
		return cartService.showCart(model, session);
	}
	
	@RequestMapping(value= {"/user/delete/{id}"})
	private String deleteProduct(@PathVariable Long id) {
		cartService.deleteCart(id);
		return "redirect: /trua_nay_an_gi/user/cart";
	}
	

	
	
	
}
