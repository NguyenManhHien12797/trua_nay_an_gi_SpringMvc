package shopbaeFood.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shopbaeFood.model.Cart;
import shopbaeFood.model.OrderDetail;
import shopbaeFood.model.dto.CartDTO;
import shopbaeFood.model.dto.MessageResponse;
import shopbaeFood.service.ICartService;
import shopbaeFood.service.IOrderDetailService;

@Controller
public class CartController {

	private final ICartService cartService;
	private final IOrderDetailService orderDetailService;

	public CartController(ICartService cartService, IOrderDetailService orderDetailService) {
		this.cartService = cartService;
		this.orderDetailService = orderDetailService;
	}

	/**
	 * This method is used to add to cart
	 * 
	 * @param cartDTO
	 * @param session
	 * @return view merchant-detail page
	 */
	@PostMapping("/user/addToCart")
	@ResponseBody
	public MessageResponse addToCart(@RequestBody CartDTO cartDTO, HttpServletRequest request) {
		return cartService.addToCart(request, cartDTO);
	}

	/**
	 * This method returns cart_page page
	 * 
	 * @param model
	 * @param session
	 * @return view cart_page
	 */
	@GetMapping("/user/cart")
	public String showCart(HttpServletRequest request, Model model) {
		return cartService.showCart(request, model);
	}

	@GetMapping("/user/getCart/{userId}")
	@ResponseBody
	public List<Cart> getCarts(@PathVariable Long userId) {
		return cartService.getCarts(userId);
	}
	
	@GetMapping("/user/getOrderDetail/{orderId}")
	@ResponseBody
	public List<OrderDetail> getOrderDetail(@PathVariable Long orderId) {
		List<OrderDetail> orderDetails = orderDetailService.findOrderDetailsByOrderId(orderId);
		return orderDetails;
	}

	/**
	 * This method is used to delete product in cart and return cart_page page
	 * 
	 * @param id : cart_id
	 * @return view car_page
	 */
	@RequestMapping(value = { "/user/delete/{id}" })
	public String deleteProduct(@PathVariable Long id) {
		cartService.deleteCart(id);
		return "redirect: /shopbaeFood/user/cart";
	}
	
	@RequestMapping(value = { "/user/increase/{cart_id}" })
	public String increaseQuantity(@PathVariable Long cart_id) {
		cartService.increaseQuantity(cart_id);
		return "redirect: /shopbaeFood/user/cart";
	}
	
	@RequestMapping(value = { "/user/decrease/{cart_id}" })
	public String decreaseQuantity(@PathVariable Long cart_id) {
		cartService.decreaseQuantity(cart_id);
		return "redirect: /shopbaeFood/user/cart";
	}
	
	@PostMapping("/user/change-quantity/{cart_id}")
	@ResponseBody
	public MessageResponse changeQuantity(@RequestBody int quantity, @PathVariable Long cart_id) {
		return cartService.changeQuantity(cart_id, quantity);
	}

}
