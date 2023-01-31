//package trua_nay_an_gi.service.seviceImpl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import trua_nay_an_gi.model.CartDetail;
//import trua_nay_an_gi.repository.ICartDetailRepository;
//import trua_nay_an_gi.service.ICartDetailService;
//
//@Service
//public class CartDetailServiceImpl implements ICartDetailService{
//	
//	@Autowired
//	private ICartDetailRepository cartDetailRepository;
//
//	@Override
//	public CartDetail findById(Long id) {
//		return cartDetailRepository.findById(id);
//	}
//
//	@Override
//	public void save(CartDetail cartDetail) {
//		cartDetailRepository.save(cartDetail);
//		
//	}
//
//	@Override
//	public void update(CartDetail cartDetail) {
//		cartDetailRepository.update(cartDetail);
//		
//	}
//
//	@Override
//	public List<CartDetail> findAll() {
//		return cartDetailRepository.findAll();
//	}
//
//	@Override
//	public CartDetail findCartDetailByProductId(Long product_id) {
//		return cartDetailRepository.findCartDetailByProductId(product_id);
//	}
//
//	@Override
//	public List<CartDetail> findAllCartDetailByUserIdAndDeleteFlag(Long userId) {
//		return cartDetailRepository.findAllCartDetailByUserIdAndDeleteFlag(userId);
//	}
//
//}
