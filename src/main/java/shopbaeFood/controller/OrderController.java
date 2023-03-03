package shopbaeFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
	public RedirectView checkPrice(@ModelAttribute("order") Order order, Model model, RedirectAttributes redirectAttributes) {
		if(!orderService.listProductChangePrice().isEmpty()) {
			redirectAttributes.addFlashAttribute("listProductChangePrice", orderService.listProductChangePrice());
			return new RedirectView("/user/cart", true);
		}
		if(!orderService.listProductDelete().isEmpty()) {
			redirectAttributes.addFlashAttribute("listProductDelete", orderService.listProductDelete());
			return new RedirectView("/user/cart", true);
		}
		if(!orderService.listProductOutOfStock().isEmpty()) {
			redirectAttributes.addFlashAttribute("listProductOutOfStock", orderService.listProductOutOfStock());
			return new RedirectView("/user/cart", true);
		}
		orderService.checkout(order, redirectAttributes);
		return new RedirectView("/user/cart", true);
	}

	@PostMapping("/user/checkout/continute")
	public RedirectView checkout(@ModelAttribute("order") Order order, Model model, RedirectAttributes redirectAttributes) {
		if(!orderService.listProductDelete().isEmpty()) {
			redirectAttributes.addFlashAttribute("listProductDelete", orderService.listProductDelete());
			return new RedirectView("/user/cart", true);
		}
		orderService.checkout(order, redirectAttributes);
		return new RedirectView("/user/cart", true);
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
	@RequestMapping(value={"/user/delete-order/{order_id}"})
	public String deleteOrder(@PathVariable Long order_id) {
		orderService.deleteOrder(order_id);
		return "redirect:/user/cart";
	}

}
