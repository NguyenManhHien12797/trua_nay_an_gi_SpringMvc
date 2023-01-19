package trua_nay_an_gi.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.Merchant;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.ProductForm;
import trua_nay_an_gi.service.IProductService;

@Controller
public class MerchantController {
	
	@Autowired
	private IProductService productService;
	
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
		if("merchant-product-manager".equals(route)) {
			List<Product> products = productService.findAllProductByDeleteFlag(session);
			
			model.addAttribute("products", products);
		}
		model.addAttribute("message", message);
		model.addAttribute("navTitle", navTitle);
		model.addAttribute("role", "merchant");
		model.addAttribute("route", route);
		model.addAttribute("productForm", new ProductForm());
		return "merchant_page";
	}
	
	@GetMapping(value= {"/merchant/{route}/{status}"})
	private String showFormOrder(@PathVariable String route, @PathVariable String status,Model model) {
		String rt = status;
		model.addAttribute("route", rt);
		model.addAttribute("status", status);
		
		return "merchant_page";
	}
	
	
	@GetMapping(value= {"/merchant/{route}/edit/{id}"})
	private String showFormUpdate(@PathVariable String route,@PathVariable Long id, Model model) {
		Product product = productService.findById(id);
		ProductForm productForm = new ProductForm(product.getId(), product.getName(), product.getShortDescription(), product.getNumberOrder(), product.getOldPrice(), product.getNewPrice(), null);
		model.addAttribute("productForm", productForm); 
		return "merchant_page";
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
