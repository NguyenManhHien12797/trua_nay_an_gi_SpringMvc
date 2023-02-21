package shopbaeFood.service.seviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import shopbaeFood.model.Account;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Product;
import shopbaeFood.model.ProductForm;
import shopbaeFood.repository.IProductRepository;
import shopbaeFood.service.IProductService;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

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
		Account account = (Account) session.getAttribute("user");

		return productRepository.findAllProductByDeleteFlag(account.getMerchant());
	}

	@Override
	public String saveProduct(ProductForm productForm, HttpSession session) {

		Account account = (Account) session.getAttribute("user");
		MultipartFile multipartFile = productForm.getImage();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Product product = new Product();
		product.setName(productForm.getName());
		product.setShortDescription(productForm.getShortDescription());
		product.setOldPrice(productForm.getOldPrice());
		product.setNewPrice(productForm.getNewPrice());
		product.setImage(fileName);
		product.setMerchant(account.getMerchant());
		productRepository.save(product);
		return "redirect: /shopbaeFood/merchant/merchant-product-manager";

	}

	@Override
	public void updateProduct(Long id, ProductForm productForm) {
		Product product = productRepository.findById(id);
		MultipartFile multipartFile = productForm.getImage();
		String fileName = multipartFile.getOriginalFilename();
		try {
			FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			fileName = product.getImage();

		}

		product.setId(product.getId());
		product.setName(productForm.getName());
		product.setShortDescription(productForm.getShortDescription());
		product.setOldPrice(productForm.getOldPrice());
		product.setNewPrice(productForm.getNewPrice());
		product.setImage(fileName);
		productRepository.update(product);

	}

	@Override
	public List<Product> findAllProductByDeleteFlag(Merchant merchant) {
		return productRepository.findAllProductByDeleteFlag(merchant);
	}

	@Override
	public List<Product> findAllProductByDeleteFlag(Merchant merchant, int pageNumber) {
		return productRepository.findAllProductByDeleteFlag(merchant, pageNumber);
	}

	@Override
	public Long lastPageNumber(Merchant merchant) {
		return productRepository.lastPageNumber(merchant);
	}

}
