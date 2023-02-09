package shopbaeFood.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import shopbaeFood.model.dto.CartDTO;
import shopbaeFood.service.ICartService;

@Controller
public class CartController {

	@Autowired
	private ICartService cartService;

	// Thêm sản phẩm vào giỏ hàng
	@PostMapping("/user/addToCart")
	private String addToCart(@RequestBody CartDTO cartDTO, HttpSession session) {

		return cartService.addToCart(cartDTO, session);
	}

	// Show giỏ hàng
	@GetMapping("/user/cart")
	private String showCart(Model model, HttpSession session) {
		return cartService.showCart(model, session);
	}

	// Xóa sản phẩm trong giỏ hàng
	@RequestMapping(value = { "/user/delete/{id}" })
	private String deleteProduct(@PathVariable Long id) {
		cartService.deleteCart(id);
		return "redirect: /shopbaeFood/user/cart";
	}

}
