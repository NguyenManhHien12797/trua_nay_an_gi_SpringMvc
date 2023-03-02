package shopbaeFood.service.seviceImpl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import shopbaeFood.model.Account;
import shopbaeFood.model.AppUser;
import shopbaeFood.model.Cart;
import shopbaeFood.model.Order;
import shopbaeFood.model.Product;
import shopbaeFood.model.ProductCartMap;
import shopbaeFood.model.dto.CartDTO;
import shopbaeFood.repository.IAppUserRepository;
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
	private IAppUserRepository userRepository;

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
	public String addToCart(CartDTO cartDTO, HttpSession session) {
		Account account = accountService.getAccount();

		if (account == null) {
			return "redirect:/login?mess=not-logged-in";
		}

		Cart cart = cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), cartDTO.getUser_id());
		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(cartDTO.getUser_id());

		int quantity = 1;
		Double totalPrice = 0.0;
		Product product = productRepository.findById(cartDTO.getProduct_id());
		Long merchant_id = null;
		if (!carts.isEmpty() && carts.get(0).getProduct().getMerchant().getId() != product.getMerchant().getId()) {
			return null;
		}

		if (cart != null) {

			cart.setQuantity(cart.getQuantity() + 1);
			totalPrice = cart.getQuantity() * cartDTO.getPrice();
			cart.setTotalPrice(totalPrice);
			cartRepository.update(cart);

			merchant_id = cart.getProduct().getMerchant().getId();
			return MessageFormat.format("redirect:/home/merchant-detail/{0}", merchant_id) ;
		} else {

			totalPrice = quantity * cartDTO.getPrice();
			AppUser user = userRepository.findById(cartDTO.getUser_id());
			boolean deleteFlag = false;

			cartRepository.save(new Cart(quantity, cartDTO.getPrice(), user, product, totalPrice, deleteFlag));
			Cart cart1 = cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), cartDTO.getUser_id());

			ProductCartMap productCartMap = new ProductCartMap(cart1, product);

			cartRepository.setProductCart(productCartMap);

			merchant_id = cart1.getProduct().getMerchant().getId();
			return MessageFormat.format("redirect:/home/merchant-detail/{0}", merchant_id);
		}

	}

	@Override
	public void deleteCart(Long id) {
		Cart cart = cartRepository.findById(id);
		cart.setDeleteFlag(true);
		cartRepository.update(cart);

	}

	@Override
	public String showCart(Model model, HttpSession session) {
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
		Long merchant_id = null;
		for (Cart cart : carts) {
			totalPrice += cart.getTotalPrice();
			merchant_id = cart.getProduct().getMerchant().getId();

		}

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
