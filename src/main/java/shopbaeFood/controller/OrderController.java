package shopbaeFood.controller;


import java.util.List;
import java.util.Map;

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
import shopbaeFood.model.Product;
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
	public RedirectView checkProductOrCheckout(@ModelAttribute("order") Order order, Model model, RedirectAttributes redirectAttributes) {
		Map<String, List<Product>> productMap = orderService.productMap(order,redirectAttributes);
	
			if(productMap.containsKey("listProductChangePrice") && 
					!productMap.containsKey("listProductDelete") && 
					!productMap.containsKey("listProductOutOfStock")) {
				redirectAttributes.addFlashAttribute("showButton", "showButton");
			}

		return new RedirectView("/user/cart", true);
	}


	@PostMapping("/user/checkout/continute")
	public RedirectView checkout(@ModelAttribute("order") Order order, Model model, RedirectAttributes redirectAttributes) {
		Map<String, List<Product>> productMap = orderService.productMap(order,redirectAttributes);
		
		if(productMap.containsKey("listProductDelete") || productMap.containsKey("listProductOutOfStock")) {
			return new RedirectView("/user/cart", true);
		}

		orderService.checkout(order, redirectAttributes);
		redirectAttributes.addFlashAttribute("removeProductMap", "removeProductMap");
		
	
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
