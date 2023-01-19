package trua_nay_an_gi.service;

import java.util.List;

import trua_nay_an_gi.model.dto.CartDTO;

public interface ICartService<Cart> {
	
  	List<Cart> findAllByUserId(Long userId);

  	void saveCart(int quantity, double price, Long userID, Long productId, double totalPrice);

    Cart findCartById(Long id);

    Cart findCartByProductIdAndUserId(Long product_id, Long user_id);
    
    void setProductCart(Long cart_id, Long product_id);

    
    void updateQuantityCart(int quantity,Long cart_id);
    
    void addToCart(CartDTO cart);

}
