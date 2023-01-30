package trua_nay_an_gi.repository;

import java.util.List;

import trua_nay_an_gi.model.Cart;
import trua_nay_an_gi.model.ProductCartMap;




public interface ICartRepository extends IGeneralRepository<Cart>{


	    Cart findCartByProductIdAndUserId(Long product_id, Long user_id);
	    
	    void setProductCart(ProductCartMap productCartMap);

	    List<Cart> findAllCartByUserIdAndDeleteFlag(Long userId);

	    @Override
	    void save(Cart cart);

		@Override
		Cart findById(Long id);

		@Override
		void update(Cart t);

		@Override
		List<Cart> findAll();
	
}
