package trua_nay_an_gi.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.model.MerchantForm;
import trua_nay_an_gi.model.Order;
import trua_nay_an_gi.model.OrderDetail;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.ProductForm;
import trua_nay_an_gi.service.IAuthenService;
import trua_nay_an_gi.service.IMerchantService;
import trua_nay_an_gi.service.IOrderDetailService;
import trua_nay_an_gi.service.IOrderService;
import trua_nay_an_gi.service.IProductService;

@Controller
public class MerchantController {
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private IOrderDetailService orderDetailService;
	
	
	@Autowired
	private IMerchantService merchantService;
	
	@Value("${file-upload}")
	private String fileUpload;

	@GetMapping("/merchant")
	private String merchantPage() {
		return "redirect: /trua_nay_an_gi/merchant/merchant-dashboard";
	}


	@GetMapping(value = {"/merchant/{route}"})
	private String merchantPage1(@PathVariable String route, Model model, HttpSession session) {
		String message = " ";
		String navTitle = "Kênh Merchant";
		Long merchant_id = (Long) session.getAttribute("userId");
		if("merchant-product-manager".equals(route)) {
			List<Product> products = productService.findAllProductByDeleteFlag(session);
			
			model.addAttribute("products", products);
			model.addAttribute("rt", route);
		}
		
		Account account = (Account) session.getAttribute("user");
		
		Merchant merchant = merchantService.findById(merchant_id);
		model.addAttribute("account", account);
		model.addAttribute("merchant", merchant);
		
		model.addAttribute("status", route);
		model.addAttribute("message", message);
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("role", "merchant");
		model.addAttribute("route", route);
		model.addAttribute("productForm", new ProductForm());
		return "merchant_page";
	}
	
	@GetMapping(value= {"/merchant/{route}/{status}"})
	private String showFormOrder(@PathVariable String route, @PathVariable String status,Model model, HttpSession session) {
		Long merchant_id = (Long) session.getAttribute("userId");
		String navTitle = "Kênh Merchant";
		List<Order> orders = orderService.findOrdersByMerchant(merchant_id,status);
		if("order-history".equals(status)) {
			orders = orderService.findOrdersByMerchantIdAndStatus(merchant_id,"buyer-receive", "buyer-refuse");
		}
		String rt = status;
		model.addAttribute("route", rt);
		model.addAttribute("status", status);
		model.addAttribute("orders", orders);
		model.addAttribute("rt", route);
		model.addAttribute("navTitle", navTitle);
		return "merchant_page";
	}
	
	/* view orderDetail */
	@GetMapping(value= {"/merchant/order/{route}/{order_id}"})
	private String showFormOrderDetail(@PathVariable String route,@PathVariable Long order_id,Model model, HttpSession session) {
		String navTitle = "Kênh Merchant";
		List<OrderDetail> orderDetails = orderDetailService.findOrderDetailsByOrderId(order_id);
		Order order = orderService.findById(order_id);
	
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("order_id", order_id);
		model.addAttribute("status", order.getStatus());
		model.addAttribute("navTitle", navTitle);
		return "merchant_page";
	}
	
	
	
	@GetMapping(value= {"/merchant/merchant-product-manager/{route}/edit/{id}"})
	private String showFormUpdate(@PathVariable String route,@PathVariable Long id, Model model) {
		String navTitle = "Kênh Merchant";
		Product product = productService.findById(id);
		ProductForm productForm = new ProductForm(product.getId(), product.getName(), product.getShortDescription(), product.getNumberOrder(), product.getOldPrice(), product.getNewPrice(), null);
		if("merchant-add-product".equals(route)) {
			productForm = new ProductForm();
		}
		model.addAttribute("productForm", productForm); 
		model.addAttribute("route",route); 
		model.addAttribute("rt","merchant-product-manager"); 
		model.addAttribute("navTitle", navTitle);
		return "merchant_page";
	}
	
	@GetMapping(value= {"/merchant/merchant-product-manager/{route}"})
	private String showFormCreate(@PathVariable String route, Model model) {
		String navTitle = "Kênh Merchant";
		model.addAttribute("productForm", new ProductForm()); 
		model.addAttribute("route",route); 
		model.addAttribute("rt","merchant-product-manager"); 
		model.addAttribute("navTitle", navTitle);
		return "merchant_page";
	}
	
	@GetMapping(value= {"/merchant/merchant-info/{route}"})
	private String showFormUpdateInfo(@PathVariable String route, Model model, HttpSession session) {
		String navTitle = "Kênh Merchant";
		Account account = (Account) session.getAttribute("user");
		Merchant merchant = merchantService.findById(account.getMerchant().getId());
		MerchantForm merchantForm = new MerchantForm(
				merchant.getId(),
				merchant.getName(), merchant.getAddress(),
				merchant.getPhone(),merchant.getOpenTime(),
				merchant.getCloseTime());
		
		model.addAttribute("account", account);
		
		model.addAttribute("route",route);
		model.addAttribute("rt","merchant-info");
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("merchantForm", merchantForm);
		model.addAttribute("merchant", merchant);
		return "merchant_page";
	}
	

	@PostMapping(value= {"/merchant/merchant-info"})
	private String updateAccountMerchant(@ModelAttribute("merchantForm") MerchantForm merchantForm, @ModelAttribute("account") Account account, HttpSession session) {

		merchantService.updateMerchantInfo(merchantForm, account, session);
		return "redirect:/merchant/merchant-info";
	}
	
	
	@PostMapping(value= {"/merchant/merchant-edit-product/edit/{id}"})
	private String updateProduct(@PathVariable Long id,@ModelAttribute("productForm") ProductForm productForm) {
		productService.updateProduct(id, productForm);
		return "redirect: /trua_nay_an_gi/merchant/merchant-product-manager";
	}
	
	@PostMapping(value= {"/merchant/create"})
	private String createProduct(@ModelAttribute("productForm") ProductForm productForm, HttpSession session) {
		return productService.saveProduct(productForm, session);
	}
	
	
	@RequestMapping(value= {"/merchant/delete/{id}"})
	private String deleteProduct(@PathVariable Long id) {
		productService.delete(id);
		return "redirect: /trua_nay_an_gi/merchant/merchant-product-manager";
	}
	

}
