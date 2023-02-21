package shopbaeFood.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import shopbaeFood.model.Account;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.MerchantForm;
import shopbaeFood.model.Order;
import shopbaeFood.model.OrderDetail;
import shopbaeFood.model.Product;
import shopbaeFood.model.ProductForm;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.service.IOrderDetailService;
import shopbaeFood.service.IOrderService;
import shopbaeFood.service.IProductService;
import shopbaeFood.util.Contants;

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

	/**
	 * This function return merchant-dashboard page
	 * @return view merchant-dashboard
	 */
	@GetMapping("/merchant")
	private String merchantPage() {
		return "redirect: /shopbaeFood/merchant/merchant-dashboard";
	}

	/**
	 * This method return merchant_page page by route
	 * @param route : merchant-dashboard/ order-manager/ merchant-product-manager/ merchant-info
	 * @param model
	 * @param session
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/{route}" })
	private String merchantPage1(@PathVariable String route, Model model, HttpSession session) {
		String message = " ";
		String navTitle = "Kênh Merchant";
		Long merchant_id = (Long) session.getAttribute("userId");
		Account account = (Account) session.getAttribute("user");
		Merchant merchant = merchantService.findById(merchant_id);
		if ("merchant-product-manager".equals(route)) {
			String firstPage = "1";
			return "redirect: /shopbaeFood/merchant/merchant-product-manager/page/"+firstPage;
		}

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

	@GetMapping(value = { "/merchant/{route}/page/{pageNumber}" })
	private String showListProduct(@PathVariable String route,@PathVariable int pageNumber, Model model, HttpSession session) {
		String message = " ";
		String navTitle = "Kênh Merchant";
		Long merchant_id = (Long) session.getAttribute("userId");
		Account account = (Account) session.getAttribute("user");
		Merchant merchant = merchantService.findById(merchant_id);
		List<Product> products = productService.findAllProductByDeleteFlag(merchant,pageNumber);
		Long lastPageNumber = productService.lastPageNumber(merchant);
		model.addAttribute("products", products);
		model.addAttribute("account", account);
		model.addAttribute("merchant", merchant);
		model.addAttribute("message", message);
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("role", "merchant");
		model.addAttribute("route", route);
		model.addAttribute("navRoute", route);
		model.addAttribute("page", pageNumber);
		model.addAttribute("lastPageNumber", lastPageNumber);
		model.addAttribute("productForm", new ProductForm());
		return "merchant_page";
	}

	/**
	 * This method return merchant_page page by route and status
	 * @param status : order-pending/ seller-receive/ order-history
	 * @param model
	 * @param session
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/order-manager/{status}/{pageNumber}" })
	private String showFormOrder( @PathVariable String status, @PathVariable int pageNumber, Model model,
			HttpSession session) {
		Long merchant_id = (Long) session.getAttribute("userId");
		String navTitle = "Kênh Merchant";
//		List<Order> orders = orderService.findOrdersByMerchant(merchant_id, status);
		List<Order> orders = orderService.findAllOrderByMerchant_idAndDeleteFlag(merchant_id, status,pageNumber);
		Long lastPageNumber = orderService.lastPageNumber(merchant_id,status);
		if (Contants.ORDER_STATE.HISTORY.equals(status)) {
			orders = orderService.findOrdersByMerchantIdAndStatus(merchant_id, Contants.ORDER_STATE.BUYER_RECEIVE, Contants.ORDER_STATE.BUYER_REFUSE);
		}
		model.addAttribute("route", status);
		model.addAttribute("status", status);
		model.addAttribute("orders", orders);
		model.addAttribute("navRoute", "order-manager");
		model.addAttribute("lastPageNumber", lastPageNumber);
		model.addAttribute("page", pageNumber);
		model.addAttribute("navTitle", navTitle);
		return "merchant_page";
	}

	/**
	 * This method returns merchant_page page by route: order_detail
	 * @param status : order-pending/ seller-receive/ order-history
	 * @param route : order-detail
	 * @param order_id
	 * @param model
	 * @param session
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/{status}/{route}/{order_id}" })
	private String showFormOrderDetail(@PathVariable String status,@PathVariable String route, @PathVariable Long order_id, Model model,
			HttpSession session) {
		String navTitle = "Kênh Merchant";
		List<OrderDetail> orderDetails = orderDetailService.findOrderDetailsByOrderId(order_id);

		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("order_id", order_id);
		model.addAttribute("status", status);
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("navRoute", "order-manager");
		return "merchant_page";
	}

	/**
	 * This method returns form update product
	 * @param route : merchant-edit-product
	 * @param id : product_id
	 * @param model
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/merchant-product-manager/{route}/edit/{id}" })
	private String showFormUpdate(@PathVariable String route, @PathVariable Long id, Model model) {
		String navTitle = "Kênh Merchant";
		Product product = productService.findById(id);
		ProductForm productForm = new ProductForm(product.getId(), product.getName(), product.getShortDescription(),
				product.getNumberOrder(), product.getOldPrice(), product.getNewPrice(), null);
		if ("merchant-add-product".equals(route)) {
			productForm = new ProductForm();
		}
		model.addAttribute("productForm", productForm);
		model.addAttribute("route", route);
		model.addAttribute("navRoute", "merchant-product-manager");
		model.addAttribute("navTitle", navTitle);
		return "merchant_page";
	}

	/**
	 * This method returns form create product
	 * @param route : merchant-add-product
	 * @param model
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/merchant-product-manager/{route}" })
	private String showFormCreate(@PathVariable String route, Model model) {
		String navTitle = "Kênh Merchant";
		model.addAttribute("productForm", new ProductForm());
		model.addAttribute("route", route);
		model.addAttribute("navRoute", "merchant-product-manager");
		model.addAttribute("navTitle", navTitle);
		return "merchant_page";
	}

	/**
	 * This method returns form merchant-update-info
	 * @param route : merchant-update-info
	 * @param model
	 * @param session
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/merchant-info/{route}" })
	private String showFormUpdateInfo(@PathVariable String route, Model model, HttpSession session) {
		String navTitle = "Kênh Merchant";
		Account account = (Account) session.getAttribute("user");
		Merchant merchant = merchantService.findById(account.getMerchant().getId());
		MerchantForm merchantForm = new MerchantForm(merchant.getId(), merchant.getName(), merchant.getAddress(),
				merchant.getPhone(), merchant.getOpenTime(), merchant.getCloseTime());

		model.addAttribute("account", account);

		model.addAttribute("route", route);
		model.addAttribute("navRoute", "merchant-info");
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("merchantForm", merchantForm);
		model.addAttribute("merchant", merchant);
		return "merchant_page";
	}

	/**
	 * This method is used to update merchant
	 * @param merchantForm
	 * @param account
	 * @param session
	 * @return view merchant-info
	 */
	@PostMapping(value = { "/merchant/merchant-info" })
	private String updateAccountMerchant(@ModelAttribute("merchantForm") MerchantForm merchantForm,
			@ModelAttribute("account") Account account, HttpSession session) {

		merchantService.updateMerchantInfo(merchantForm, account, session);
		return "redirect:/merchant/merchant-info";
	}

	/**
	 * This method is used to update product
	 * @param id : product_id
	 * @param productForm
	 * @return view merchant-product-manager
	 */
	@PostMapping(value = { "/merchant/merchant-edit-product/edit/{id}" })
	private String updateProduct(@PathVariable Long id, @ModelAttribute("productForm") ProductForm productForm) {
		productService.updateProduct(id, productForm);
		return "redirect: /shopbaeFood/merchant/merchant-product-manager";
	}

	/**
	 * This method is used to create product
	 * @param productForm
	 * @param session
	 * @return view merchant-product-manager
	 */
	@PostMapping(value = { "/merchant/create" })
	private String createProduct(@ModelAttribute("productForm") ProductForm productForm, HttpSession session) {
		return productService.saveProduct(productForm, session);
	}

	/**
	 * This method is used to delete product
	 * @param id : product_id
	 * @return view merchant-product-manager
	 */
	@RequestMapping(value = { "/merchant/delete/{id}" })
	private String deleteProduct(@PathVariable Long id) {
		productService.delete(id);
		return "redirect: /shopbaeFood/merchant/merchant-product-manager";
	}

}
