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
		return "merchant_page";
	}

	@GetMapping("/merchant/{route}")
	private String merchantPage1(@PathVariable String route, Model model, HttpSession session) {
		
		if("merchant-product-manager".equals(route)) {
			Account account = (Account) session.getAttribute("user");
			if(account == null) {
				return "/login";
			}
			List<Product> products = productService.findAllProductByDeleteFlag(account.getMerchant().getId());
			model.addAttribute("products", products);
		}
		model.addAttribute("route", route);
		return "merchant_page";
	}
	
	@GetMapping(value= {"/merchant/{route}/{status}"})
	private String merchantPage2(@PathVariable String route, @PathVariable String status,Model model) {
		String rt = status;
		model.addAttribute("route", rt);
		model.addAttribute("status", status);
		
		return "merchant_page";
	}
	
	
	@GetMapping(value= {"/merchant/{route}/edit/{id}"})
	private String merchantPage3(@PathVariable String route,@PathVariable Long id, Model model) {
		Product product = productService.findById(id);
		ProductForm productForm = new ProductForm(product.getId(), product.getName(), product.getShortDescription(), product.getNumberOrder(), product.getOldPrice(), product.getNewPrice(), null);
		model.addAttribute("productForm", productForm); 
		return "merchant_page";
	}
	
	@PostMapping(value= {"/merchant/merchant-edit-product/edit/{id}"})
	private String updateProduct(@PathVariable Long id,@ModelAttribute("productForm") ProductForm productForm, Model model) {
		System.out.println(productForm.getName());
//		System.out.println(productForm.getShortDescription());
		
		MultipartFile multipartFile = productForm.getImage();
		String fileName = "/static/img/"+multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		Product product = productService.findById(id);
		product.setId(product.getId());
		product.setName(productForm.getName());
		product.setShortDescription(productForm.getShortDescription());
		product.setOldPrice(productForm.getOldPrice());
		product.setNewPrice(productForm.getNewPrice());
		product.setImage(fileName);
		productService.update(product);
		return "redirect: /trua_nay_an_gi/merchant/merchant-product-manager";
	}
	
	
	@RequestMapping(value= {"/merchant/delete/{id}"})
	private String deleteProduct(@PathVariable Long id, Model model) {
		Product product = productService.findById(id);
		product.setDeleteFlag(true);
		productService.update(product);
		return "redirect: /trua_nay_an_gi/merchant/merchant-product-manager";
	}
	
//	@PostMapping("/product/edit/save")
//	public String editProduct(@ModelAttribute ProductForm productForm) {
//		System.out.println("eidt " + productForm);
//		MultipartFile multipartFile = productForm.getImage();
//		String fileName = multipartFile.getOriginalFilename();
//		try {
//			FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		product.setDeleteFlag(true);
//		Merchant merchant = new Merchant();
//		merchant.setId(1L);
//		product.setMerchant(merchant);
//		productService.update(product);
//		return "redirect:/merchant/product/";
//	}
	
	
}
