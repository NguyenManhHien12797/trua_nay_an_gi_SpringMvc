package trua_nay_an_gi.service.seviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.ProductForm;
import trua_nay_an_gi.repository.IProductRepository;
import trua_nay_an_gi.service.IProductService;

@Service
@Transactional
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private IProductRepository productRepository;
	
	@Value("${file-upload}")
	private String fileUpload;

	@Override
	public Product findById(Long id) {
		return productRepository.findById(id);
	}


	
	@Override
	public void save(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Product product) {
		productRepository.update(product);
		
	}

	@Override
	public void delete(Long id) {
		Product product = productRepository.findById(id);
		product.setDeleteFlag(true);
		productRepository.update(product);
		
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> findAllProductByDeleteFlag(HttpSession session) {
		Account account =(Account) session.getAttribute("user");
		if(account == null) {
			throw new BadCredentialsException("Không tìm thấy account! Mời đăng nhập lại!");
		}
		
		return productRepository.findAllProductByDeleteFlag(account.getMerchant().getId());
	}

	
	@Override
	public String saveProduct(ProductForm productForm, HttpSession session) {
		
		Account account =(Account) session.getAttribute("user");
		MultipartFile multipartFile = productForm.getImage();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(account == null) {
			throw new BadCredentialsException("Không tìm thấy account! Mời đăng nhập lại!");
		}
	
		Product product = new Product();
		product.setName(productForm.getName());
		product.setShortDescription(productForm.getShortDescription());
		product.setOldPrice(productForm.getOldPrice());
		product.setNewPrice(productForm.getNewPrice());
		product.setImage(fileName);
		product.setMerchant(account.getMerchant());
		productRepository.save(product);
		return "redirect: /trua_nay_an_gi/merchant/merchant-product-manager";
		
		
	}

	@Override
	public void updateProduct(Long id, ProductForm productForm) {
	
		MultipartFile multipartFile = productForm.getImage();
		String fileName =multipartFile.getOriginalFilename();
//		String fileName = "/static/img/"+multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Product product = productRepository.findById(id);
		product.setId(product.getId());
		product.setName(productForm.getName());
		product.setShortDescription(productForm.getShortDescription());
		product.setOldPrice(productForm.getOldPrice());
		product.setNewPrice(productForm.getNewPrice());
		product.setImage(fileName);
		productRepository.update(product);
		
	}



	@Override
	public List<Product> findAllProductByDeleteFlag(Long merchantId) {
		return productRepository.findAllProductByDeleteFlag(merchantId);
	}



}
