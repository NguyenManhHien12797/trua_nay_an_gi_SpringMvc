package shopbaeFood.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import shopbaeFood.model.Cart;
import shopbaeFood.model.dto.CartDTO;

public interface ICartService {

	List<Cart> findAllCartByUserIdAndDeleteFlag(Long userId);

	String addToCart(CartDTO cart, HttpSession session);

	String showCart(Model model, HttpSession session);

	void deleteCart(Long id);

}
