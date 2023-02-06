package trua_nay_an_gi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import trua_nay_an_gi.model.Cart;
import trua_nay_an_gi.model.dto.CartDTO;

public interface ICartService {
	
  	List<Cart> findAllCartByUserIdAndDeleteFlag(Long userId);

    String addToCart(CartDTO cart, HttpSession session);
    
    String showCart(Model model, HttpSession session);
    
    void deleteCart(Long id);

}
