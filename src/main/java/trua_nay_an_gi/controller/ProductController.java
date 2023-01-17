package trua_nay_an_gi.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.ProductForm;
import trua_nay_an_gi.service.IProductService;

@Controller
public class ProductController {
	
	@Autowired
	private IProductService productService;

	@GetMapping("/product")
	public String product(Model model) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "product";
	}
	
	
	
	
}
