package trua_nay_an_gi.repository;

import java.util.List;




public interface ICartRepository<Cart> {

	
	  	List<Cart> findAllByUserId(Long userId);

	  	void saveCart(int quantity, double price, Long userID, Long productId, double totalPrice);

	    Cart findCartById(Long id);

	    Cart findCartByProductIdAndUserId(Long product_id, Long user_id);
	    
	    void setProductCart(Long cart_id, Long product_id);

	    
	    void updateQuantityCart(int quantity,Long cart_id);

	    void save(Cart cart);
	
}
