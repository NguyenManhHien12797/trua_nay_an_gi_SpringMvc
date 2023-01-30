package trua_nay_an_gi.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.Order;
import trua_nay_an_gi.service.ICartService;
import trua_nay_an_gi.service.IOrderService;

@Controller
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ICartService cartService;

	@PostMapping("/checkout")
	public String checkout(@ModelAttribute("order") Order order, HttpSession session) {
		Account account = (Account) session.getAttribute("user");
		order.setAppUser(account.getUser());
		order.setStatus("pending");
		System.out.println(order.getNote());
		System.out.println(order.getTotalPrice());
		System.out.println(order.getMerchant_id());
		System.out.println(order.getOrderdate());
	
		System.out.println(order.getAppUser().getName());
		orderService.save(order);
		
		return "redirect: /trua_nay_an_gi/user/cart";
	}
	
}
