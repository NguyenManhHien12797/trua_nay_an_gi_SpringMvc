package trua_nay_an_gi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import trua_nay_an_gi.model.dto.CartDTO;
import trua_nay_an_gi.service.ICartService;

@Controller
public class CartController {
	
	@Autowired
	private ICartService cartService;

	@PostMapping("/addToCart")
	private String addToCart(@RequestBody CartDTO cartDTO) {
		System.out.println(cartDTO);
		cartService.addToCart(cartDTO);
		return "/merchant-details";
	}
}
