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

	/**
	 * This method is used to add to cart
	 * @param cartDTO
	 * @param session
	 * @return view merchant-detail page
	 */
	@PostMapping("/user/addToCart")
	public String addToCart(@RequestBody CartDTO cartDTO, HttpSession session) {
		return cartService.addToCart(cartDTO);
	}
	
	/**
	 * This method returns cart_page page
	 * @param model
	 * @param session
	 * @return view cart_page
	 */
	@GetMapping("/user/cart")
	public String showCart(Model model) {
		return cartService.showCart(model);
	}

	/**
	 * This method is used to delete product in cart and return cart_page page
	 * @param id : cart_id
	 * @return view car_page
	 */
	@RequestMapping(value = { "/user/delete/{id}" })
	public String deleteProduct(@PathVariable Long id) {
		cartService.deleteCart(id);
		return "redirect: /shopbaeFood/user/cart";
	}

}
