package shopbaeFood.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.service.IOrderDetailService;
import shopbaeFood.service.IOrderService;
import shopbaeFood.service.IProductService;
import shopbaeFood.util.Page;

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

	@Autowired
	private IAccountService accountService;

	@Value("${file-upload}")
	private String fileUpload;

	private final int PAGE_SIZE = 5;

	/**
	 * This function return merchant-dashboard page
	 * 
	 * @return view merchant-dashboard
	 */
	@GetMapping("/merchant")
	private String redirectMerchantPage() {
		return "redirect: /shopbaeFood/merchant/merchant-dashboard";
	}

	/**
	 * This method return merchant_page page by route
	 * 
	 * @param route : merchant-dashboard/ order-manager/ merchant-product-manager/
	 *              merchant-info
	 * @param model
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/{route}" })
	private String merchantPage(@PathVariable String route, Model model) {
		String message = " ";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountService.findByName(authentication.getName());
		Merchant merchant = account.getMerchant();
		if ("merchant-product-manager".equals(route)) {
			String firstPage = "1";
			return "redirect: /shopbaeFood/merchant/merchant-product-manager/page/" + firstPage;
		}
		addListAttribute(route, route, null, model);
		model.addAttribute("account", account);
		model.addAttribute("merchant", merchant);
		model.addAttribute("message", message);
		model.addAttribute("productForm", new ProductForm());
		return "merchant_page";
	}

	/**
	 * This method is used to show list product
	 * 
	 * @param route
	 * @param pageNumber
	 * @param model
	 * @return
	 */
	@GetMapping(value = { "/merchant/{route}/page/{pageNumber}" })
	private String showListProduct(@PathVariable String route, @PathVariable int pageNumber, Model model) {
		String message = " ";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountService.findByName(authentication.getName());
		Merchant merchant = account.getMerchant();
		Page<Product> page = new Page<Product>();
		List<Product> products = page.paging(pageNumber, PAGE_SIZE,
				productService.findAllProductByMerchantAndDeleteFlag(merchant).stream()
						.sorted(Comparator.comparingLong(Product::getId).reversed()).collect(Collectors.toList()));

		int lastPageNumber = page.lastPageNumber(PAGE_SIZE,
				productService.findAllProductByMerchantAndDeleteFlag(merchant));

		addListAttribute(null, route, route, model);
		model.addAttribute("products", products);
		model.addAttribute("account", account);
		model.addAttribute("merchant", merchant);
		model.addAttribute("message", message);
		model.addAttribute("page", pageNumber);
		model.addAttribute("lastPageNumber", lastPageNumber);
		model.addAttribute("productForm", new ProductForm());
		return "merchant_page";
	}

	/**
	 * This method return merchant_page page by route and status
	 * 
	 * @param status  : order-pending/ seller-receive/ order-history
	 * @param model
	 * @param session
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/order-manager/{status}/{pageNumber}" })
	private String showFormOrder(@PathVariable String status, @PathVariable int pageNumber, Model model,
			HttpSession session) {
		List<Order> orders = orderService.page(status, pageNumber, session).getPaging();
		int lastPageNumber = orderService.page(status, pageNumber, session).getLastPageNumber();
		addListAttribute(status, status, "order-manager", model);
		model.addAttribute("orders", orders);
		model.addAttribute("lastPageNumber", lastPageNumber);
		model.addAttribute("page", pageNumber);
		return "merchant_page";
	}

	/**
	 * This method is used to addAttribute
	 * 
	 * @param status
	 * @param route
	 * @param navRoute
	 * @param model
	 */
	private void addListAttribute(String status, String route, String navRoute, Model model) {
		String navTitle = "Kênh Merchant";
		model.addAttribute("status", status);
		model.addAttribute("route", route);
		model.addAttribute("navRoute", navRoute);
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("role", "merchant");

	}

	/**
	 * This method returns merchant_page page by route: order_detail
	 * 
	 * @param status   : order-pending/ seller-receive/ order-history
	 * @param route    : order-detail
	 * @param order_id
	 * @param model
	 * @param session
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/{status}/order-detail/{order_id}" })
	private String showOrderDetail(@PathVariable String status, @PathVariable Long order_id, Model model) {
		List<OrderDetail> orderDetails = orderDetailService.findOrderDetailsByOrderId(order_id);
		addListAttribute(status, "order-detail", "order-manager", model);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("order_id", order_id);
		return "merchant_page";
	}

	/**
	 * This method returns form update product
	 * 
	 * @param route : merchant-edit-product
	 * @param id    : product_id
	 * @param model
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/merchant-product-manager/merchant-edit-product/{id}/page/{pageNumber}" })
	private String showFormUpdateProduct(@PathVariable Long id, @PathVariable int pageNumber, Model model) {
		Product product = productService.findById(id);
		ProductForm productForm = new ProductForm(product.getId(), product.getName(), product.getShortDescription(),
				product.getNumberOrder(), product.getOldPrice(), product.getNewPrice(), product.getQuantity(), null);

		addListAttribute(null, "merchant-edit-product", "merchant-product-manager", model);
		model.addAttribute("productForm", productForm);
		model.addAttribute("page", pageNumber);
		return "merchant_page";
	}

	/**
	 * This method is used to update product
	 * 
	 * @param id          : product_id
	 * @param productForm
	 * @return view merchant-product-manager
	 */
	@PostMapping(value = { "/merchant/merchant-edit-product/{id}/page/{pageNumber}" })
	private String updateProduct(@PathVariable Long id, @PathVariable int pageNumber,
			@ModelAttribute("productForm") ProductForm productForm) {
		productService.updateProduct(id, productForm);
		System.out.println(pageNumber);
		return "redirect: /shopbaeFood/merchant/merchant-product-manager/page/" + pageNumber;
	}

	/**
	 * This method returns form create product
	 * 
	 * @param model
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/merchant-product-manager/merchant-add-product" })
	private String showFormCreateProduct(Model model) {

		addListAttribute(null, "merchant-add-product", "merchant-product-manager", model);
		model.addAttribute("productForm", new ProductForm());
		return "merchant_page";
	}

	/**
	 * This method is used to create product
	 * 
	 * @param productForm
	 * @return view merchant-product-manager
	 */
	@PostMapping(value = { "/merchant/create" })
	private String createProduct(@ModelAttribute("productForm") ProductForm productForm) {
		return productService.saveProduct(productForm);
	}

	/**
	 * This method returns form merchant-update-info
	 * 
	 * @param model
	 * @param session
	 * @return view merchant_page
	 */
	@GetMapping(value = { "/merchant/merchant-info/merchant-update-info" })
	private String showFormUpdateInfo(Model model, HttpSession session) {

		Account account = (Account) session.getAttribute("user");
		Merchant merchant = merchantService.findById(account.getMerchant().getId());
		MerchantForm merchantForm = new MerchantForm(merchant.getId(), merchant.getName(), merchant.getAddress(),
				merchant.getPhone(), merchant.getOpenTime(), merchant.getCloseTime(), merchant.getCategory());

		addListAttribute(null, "merchant-update-info", "merchant-info", model);
		model.addAttribute("account", account);
		model.addAttribute("merchantForm", merchantForm);
		model.addAttribute("merchant", merchant);
		return "merchant_page";
	}

	/**
	 * This method is used to update merchant
	 * 
	 * @param merchantForm
	 * @param account
	 * @param session
	 * @return view merchant-info
	 */
	@PostMapping(value = { "/merchant/merchant-info" })
	private String updateMerchantInfo(@ModelAttribute("merchantForm") MerchantForm merchantForm,
			@ModelAttribute("account") Account account, HttpSession session) {

		merchantService.updateMerchantInfo(merchantForm, account, session);
		return "redirect:/merchant/merchant-info";
	}

	/**
	 * This method is used to delete product
	 * 
	 * @param id : product_id
	 * @return view merchant-product-manager
	 */
	@RequestMapping(value = { "/merchant/delete/{id}" })
	private String deleteProduct(@PathVariable Long id) {
		productService.delete(id);
		return "redirect: /shopbaeFood/merchant/merchant-product-manager";
	}

}
