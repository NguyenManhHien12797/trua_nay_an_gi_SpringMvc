package shopbaeFood.service.seviceImpl;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import shopbaeFood.model.Account;
import shopbaeFood.model.Cart;
import shopbaeFood.model.Order;
import shopbaeFood.model.Product;
import shopbaeFood.model.ProductCartMap;
import shopbaeFood.model.dto.CartDTO;
import shopbaeFood.model.dto.MessageResponse;
import shopbaeFood.repository.ICartRepository;
import shopbaeFood.repository.IOrderRepository;
import shopbaeFood.repository.IProductRepository;
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.ICartService;
import shopbaeFood.util.Constants;

@Service
@Transactional
public class CartServiceImpl implements ICartService {

	@Autowired
	private ICartRepository cartRepository;

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private IOrderRepository orderRepository;
	
	@Autowired
	private IAccountService accountService;

	@Override
	public List<Cart> findAllCartByUserIdAndDeleteFlag(Long userId) {

		return cartRepository.findAllCartByUserIdAndDeleteFlag(userId);
	}

	@Override
	public MessageResponse addToCart(CartDTO cartDTO) {
		Account account = accountService.getAccount();
		MessageResponse mess = new MessageResponse();
		if (account == null) {
			mess.setMessage("not-logged-in");
			return mess;
		}

		Cart cart = cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), account.getUser().getId());
		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());
		
		int quantity = 1;
		Double totalPrice = 0.0;
		Product product = productRepository.findById(cartDTO.getProduct_id());
		if (!carts.isEmpty() && carts.get(0).getProduct().getMerchant().getId() != product.getMerchant().getId()) {
			mess.setMessage("other shop");
			return mess;
		}

		if (cart != null) {
			if(product.getQuantity()<=0) {
				mess.setMessage("out of stock");
				return mess;
			}
			cart.setQuantity(cart.getQuantity() + 1);
			totalPrice = cart.getQuantity() * cartDTO.getPrice();
			cart.setTotalPrice(totalPrice);
			cartRepository.update(cart);

			mess.setMessage("add suscess");
			return mess;
		} else {
			if(product.getQuantity()<=0) {
				mess.setMessage("out of stock");
				return mess;
			}
			totalPrice = quantity * cartDTO.getPrice();
			boolean deleteFlag = false;

			cartRepository.save(new Cart(quantity, cartDTO.getPrice(), account.getUser(), product, totalPrice, deleteFlag));
			Cart cart1 = cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), cartDTO.getUser_id());

			ProductCartMap productCartMap = new ProductCartMap(cart1, product);

			cartRepository.setProductCart(productCartMap);

			mess.setMessage("add suscess");
			return mess;
		}

	}

	@Override
	public void deleteCart(Long id) {
		Cart cart = cartRepository.findById(id);
		cart.setDeleteFlag(true);
		cartRepository.update(cart);

	}

	@Override
	public String showCart(Model model) {
		Account account = accountService.getAccount();
		if (account == null) {
			return "redirect:/login?mess=not-logged-in";
		}
		Long userId = account.getUser().getId();
		
		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(userId);
		List<Order> orders = orderRepository.findOrdersByUserId(userId);
		String message = " ";
		if (carts.isEmpty()) {
			message = Constants.CART_MESSAGE.NO_DATA;
		}

		Double totalPrice = 0.0;
		Double cartTotalPrice = 0.0;
		Long merchant_id = null;
		for (Cart cart : carts) {
			cartTotalPrice = cart.getQuantity()* cart.getProduct().getNewPrice();
			cart.setTotalPrice(cartTotalPrice);
			totalPrice += cart.getTotalPrice();
			merchant_id = cart.getProduct().getMerchant().getId();

		}
	         
		    // tạo 1 NumberFormat để định dạng tiền tệ theo tiêu chuẩn của Việt Nam
		    // đơn vị tiền tệ của Việt Nam là đồng
		    Locale localeVN = new Locale("vi", "VN");
		    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		    String str1 = currencyVN.format(totalPrice);
		Order order = new Order();
		order.setMerchant_id(merchant_id);
		LocalDateTime time = LocalDateTime.now();
		order.setOrderdate(time);
		order.setAppUser(account.getUser());

		model.addAttribute("carts", carts);
		model.addAttribute("message", message);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("order", order);
		model.addAttribute("orders", orders);
		model.addAttribute("role", "user");
		return "/cart_page";
	}

}
