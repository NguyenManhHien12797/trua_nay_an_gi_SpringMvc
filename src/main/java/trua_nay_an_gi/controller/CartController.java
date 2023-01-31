package trua_nay_an_gi.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import trua_nay_an_gi.model.Cart;
import trua_nay_an_gi.model.Order;
import trua_nay_an_gi.model.dto.CartDTO;
import trua_nay_an_gi.service.ICartService;

@Controller
public class CartController {
	
	@Autowired
	private ICartService cartService;
	

	@PostMapping("/addToCart")
	private String addToCart(@RequestBody CartDTO cartDTO, HttpSession session) {
		
		return cartService.addToCart(cartDTO, session);
	}
	
	
	@GetMapping("/user/cart")
	private String showCart(Model model, HttpSession session) {
		Long userId  = (Long) session.getAttribute("userId");
		List<Cart> carts = cartService.findAllCartByUserIdAndDeleteFlag(userId);
		
		String message = " ";
		if(carts.isEmpty()) {
			message = "khong co du lieu";
		}
		
		Double totalPrice = 0.0;
		for(Cart cart: carts) {
			totalPrice += cart.getTotalPrice();
		}
		Order order = new Order();
	
		order.setTotalPrice(totalPrice); 
		LocalDateTime time = LocalDateTime.now();
		order.setOrderdate(time);
		
		model.addAttribute("carts", carts);
		model.addAttribute("message", message);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("order", order);
		return "/cart_page";
	}
	
	@RequestMapping(value= {"/user/delete/{id}"})
	private String deleteProduct(@PathVariable Long id) {
		cartService.deleteCart(id);
		return "redirect: /trua_nay_an_gi/user/cart";
	}
	
}
