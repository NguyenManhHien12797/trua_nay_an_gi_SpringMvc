package trua_nay_an_gi.service.seviceImpl;


import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.Cart;
import trua_nay_an_gi.model.Order;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.ProductCartMap;
import trua_nay_an_gi.model.dto.CartDTO;
import trua_nay_an_gi.repository.IAppUserRepository;
import trua_nay_an_gi.repository.ICartRepository;
import trua_nay_an_gi.repository.IOrderRepository;
import trua_nay_an_gi.repository.IProductRepository;
import trua_nay_an_gi.service.ICartService;

@Service
@Transactional
public class CartServiceImpl implements ICartService{
	
	@Autowired
	private ICartRepository cartRepository;
	
	
	@Autowired
	private IAppUserRepository userRepository;
	
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private IOrderRepository orderRepository;
	

	@Override
	public List<Cart> findAllCartByUserIdAndDeleteFlag(Long userId) {
	
		return cartRepository.findAllCartByUserIdAndDeleteFlag(userId);
	}
	
	@Override
	public String addToCart(CartDTO cartDTO, HttpSession session) {
		
	
		
		Account account = (Account) session.getAttribute("user");
		
		if(account == null) {
			return "/login?mess=chua-dang-nhap";
		}
		
        Cart cart = cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), cartDTO.getUser_id());
        List<Cart>carts = cartRepository.findAllCartByUserIdAndDeleteFlag(cartDTO.getUser_id());
        
        int quantity = 1;
        Double totalPrice = 0.0;
        Product product = productRepository.findById(cartDTO.getProduct_id());
        Long merchant_id = null;
        if(!carts.isEmpty() && carts.get(0).getProduct().getMerchant().getId() != product.getMerchant().getId()) {
        	return null;
        }
        
        if (cart != null) {
        	
        	cart.setQuantity(cart.getQuantity() + 1);
        	totalPrice = cart.getQuantity()*cartDTO.getPrice();
        	cart.setTotalPrice(totalPrice);
            cartRepository.update(cart);
            
            merchant_id = cart.getProduct().getMerchant().getId();
            return "redirect:/home/merchant-detail/"+merchant_id ;
        } else {

            totalPrice = quantity*cartDTO.getPrice();
            AppUser user = userRepository.findById(cartDTO.getUser_id());
            boolean deleteFlag = false;

            cartRepository.save(new Cart(quantity, cartDTO.getPrice(), user,product, totalPrice, deleteFlag));
            Cart cart1 =cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), cartDTO.getUser_id());
           
            ProductCartMap productCartMap = new ProductCartMap(cart1, product);
            
            cartRepository.setProductCart(productCartMap);
            
            merchant_id = cart1.getProduct().getMerchant().getId();
            return "redirect:/home/merchant-detail/"+merchant_id ;
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
		Long userId  = (Long) session.getAttribute("userId");
		Account account = (Account) session.getAttribute("user");
		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(userId);
		List<Order>orders = orderRepository.findOrdersByUserId(userId);
		String message = " ";
		if(carts.isEmpty()) {
			message = "khong co du lieu";
		}
		
		Double totalPrice = 0.0;
		Long merchant_id = null;
		for(Cart cart: carts) {
			totalPrice += cart.getTotalPrice();
			merchant_id= cart.getProduct().getMerchant().getId();
			
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
