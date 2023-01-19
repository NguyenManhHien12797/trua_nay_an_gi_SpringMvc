package trua_nay_an_gi.service.seviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.Cart;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.dto.CartDTO;
import trua_nay_an_gi.repository.IAppUserRepository;
import trua_nay_an_gi.repository.ICartRepository;
import trua_nay_an_gi.repository.IProductRepository;
import trua_nay_an_gi.service.ICartService;

@Service
@Transactional
public class CartServiceImpl implements ICartService<Cart>{
	
	@Autowired
	private ICartRepository cartRepository;
	
	@Autowired
	private IAppUserRepository userRepository;
	
	@Autowired
	private IProductRepository productRepository;
	

	@Override
	public List<Cart> findAllByUserId(Long userId) {
		return cartRepository.findAllByUserId(userId);
	}

	@Override
	public void saveCart(int quantity, double price, Long userID, Long productId, double totalPrice) {
		
		
	}

	@Override
	public Cart findCartById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart findCartByProductIdAndUserId(Long product_id, Long user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProductCart(Long cart_id, Long product_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateQuantityCart(int quantity, Long cart_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToCart(CartDTO cartDTO) {
		
        Cart cart = (Cart) cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), cartDTO.getUser_id());
        int quantity = 1;
        if (cart != null) {
        	quantity = cart.getQuantity() + 1;
            cartRepository.updateQuantityCart(quantity, cart.getId());
        } else {

            Double totalPrice = quantity*cartDTO.getPrice();
            AppUser user = userRepository.findById(cartDTO.getUser_id());
            Product product = productRepository.findById(cartDTO.getProduct_id());
            
            cartRepository.saveCart(quantity, cartDTO.getPrice(), cartDTO.getUser_id(), cartDTO.getProduct_id(), totalPrice);
//            cartRepository.save(new Cart(quantity, cartDTO.getPrice(), user,product, totalPrice));
            Cart cart1 = (Cart) cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), cartDTO.getUser_id());
            cartRepository.setProductCart(cart1.getId(), cartDTO.getProduct_id());
        }
		
	}

}
