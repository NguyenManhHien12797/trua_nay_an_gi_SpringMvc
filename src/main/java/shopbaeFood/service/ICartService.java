package shopbaeFood.service;

import java.util.List;

import org.springframework.ui.Model;

import shopbaeFood.model.Cart;
import shopbaeFood.model.dto.CartDTO;
import shopbaeFood.model.dto.MessageResponse;

public interface ICartService {
	
	/**
	 * This method is used to find all cart by userId and deleteFlag= false
	 * @param userId
	 * @return List<Cart>
	 */
	List<Cart> findAllCartByUserIdAndDeleteFlag(Long userId);

	/**
	 * This method is used to add product to cart
	 * @param cart
	 * @param session
	 * @return
	 */
	MessageResponse addToCart(CartDTO cart);

	/**
	 * This method is used to show cart_page
	 * @param model
	 * @param session
	 * @return view cart_page
	 */
	String showCart(Model model);
	
	List<Cart>getCarts(Long userId);

	/**
	 * This method is used to delete cart
	 * @param id
	 */
	void deleteCart(Long id);

}
