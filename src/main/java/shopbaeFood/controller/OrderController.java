package shopbaeFood.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import shopbaeFood.model.Order;
import shopbaeFood.service.IOrderService;

@Controller
public class OrderController {

	@Autowired
	private IOrderService orderService;

	/**
	 * This method is used to checkout
	 * @param order
	 * @param session
	 * @return view cart_page
	 */
	@PostMapping("/user/checkout")
	public String checkout(@ModelAttribute("order") Order order, HttpSession session) {
		orderService.checkout(order, session);

		return "redirect: /shopbaeFood/user/cart";
	}

	/**
	 * This method is used to update order status
	 * @param order_id
	 * @param status
	 * @return 
	 */
	@RequestMapping("/user/update-order-status/{order_id}/{status}")
	public String updateOrderStatus(@PathVariable Long order_id, @PathVariable String status) {

		return orderService.updateOrderStatus(order_id, status);
	}

	/**
	 * This method is used to delete order
	 * @param order_id
	 * @return view cart_page
	 */
	@RequestMapping("/user/delete-order/{order_id}")
	public String deleteOrder(@PathVariable Long order_id) {
		orderService.deleteOrder(order_id);
		return "redirect:/shopbaeFood/user/cart";
	}

}
