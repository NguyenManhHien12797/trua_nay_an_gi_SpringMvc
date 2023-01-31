package trua_nay_an_gi.service.seviceImpl;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.AppUser;
import trua_nay_an_gi.model.Cart;
import trua_nay_an_gi.model.Product;
import trua_nay_an_gi.model.ProductCartMap;
import trua_nay_an_gi.model.dto.CartDTO;
import trua_nay_an_gi.repository.IAppUserRepository;
import trua_nay_an_gi.repository.ICartRepository;
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
	

	@Override
	public List<Cart> findAllCartByUserIdAndDeleteFlag(Long userId) {
	
		return cartRepository.findAllCartByUserIdAndDeleteFlag(userId);
	}
	
	@Override
	public String addToCart(CartDTO cartDTO, HttpSession session) {
		
	
		
		Account account = (Account) session.getAttribute("user");
		
		if(account == null) {
			return "/login";
		}
		
        Cart cart = cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), cartDTO.getUser_id());
        
        int quantity = 1;
        Double totalPrice = 0.0;
        if (cart != null) {
        	cart.setQuantity(cart.getQuantity() + 1);
        	totalPrice = cart.getQuantity()*cartDTO.getPrice();
        	cart.setTotalPrice(totalPrice);
            cartRepository.update(cart);
            return "/merchant-details";
        } else {

            totalPrice = quantity*cartDTO.getPrice();
            AppUser user = userRepository.findById(cartDTO.getUser_id());
            Product product = productRepository.findById(cartDTO.getProduct_id());
            boolean deleteFlag = false;

            cartRepository.save(new Cart(quantity, cartDTO.getPrice(), user,product, totalPrice, deleteFlag));
            Cart cart1 =cartRepository.findCartByProductIdAndUserId(cartDTO.getProduct_id(), cartDTO.getUser_id());
           
            ProductCartMap productCartMap = new ProductCartMap(cart1, product);
            
            cartRepository.setProductCart(productCartMap);
            
            return "/merchant-details";
        }
		
	}

	



	@Override
	public void deleteCart(Long id) {
		Cart cart = cartRepository.findById(id);
		cart.setDeleteFlag(true);
		cartRepository.update(cart);
		
	}







}
