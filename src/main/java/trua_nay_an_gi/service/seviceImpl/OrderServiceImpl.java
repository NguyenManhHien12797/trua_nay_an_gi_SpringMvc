package trua_nay_an_gi.service.seviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trua_nay_an_gi.model.Account;
import trua_nay_an_gi.model.Cart;
import trua_nay_an_gi.model.Order;
import trua_nay_an_gi.model.OrderDetail;
import trua_nay_an_gi.repository.ICartRepository;
import trua_nay_an_gi.repository.IOrderDetailRepository;
import trua_nay_an_gi.repository.IOrderRepository;
import trua_nay_an_gi.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService{
	
	@Autowired
	private IOrderRepository orderRepository;
	
	@Autowired
	private IOrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ICartRepository cartRepository;

	@Override
	public Order findById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public void save(Order order) {
		orderRepository.save(order);
		
	}

	@Override
	public void update(Order order) {
		orderRepository.update(order);
		
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public void checkout(Order order, HttpSession session) {
		Account account = (Account) session.getAttribute("user");
		order.setAppUser(account.getUser());
		order.setStatus("order-pending");
		orderRepository.save(order);
		
		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());

		OrderDetail orderDetail = new OrderDetail();
		
		for(Cart cart: carts) {
			orderDetail.setProduct(cart.getProduct());
			orderDetail.setQuantity(cart.getQuantity());
			orderDetail.setOrder(order);
			orderDetail.setDeleteFlag(false);
			orderDetailRepository.save(orderDetail);
			cart.setDeleteFlag(true);
			cartRepository.update(cart);
			
		}
		
	}

	@Override
	public List<Order> findOrdersByMerchant(Long merchant_id, String status) {
		return orderRepository.findOrdersByMerchantId(merchant_id, status);
	}

	@Override
	public List<Order> findOrdersByUserId(Long user_id) {
		return orderRepository.findOrdersByUserId(user_id);
	}

	@Override
	public String updateOrderStatus(Long order_id, String status) {
		Order order = orderRepository.findById(order_id);
		order.setStatus(status);
		orderRepository.update(order);
		String route = "redirect: /trua_nay_an_gi/merchant/seller-receive";
		if("buyer-receive".equals(status) || "buyer-refuse".equals(status)) {
			route = "redirect:/user/cart";
		}
		return route;
		
	}

	@Override
	public List<Order> findOrdersByMerchantIdAndStatus(Long merchant_id, String status, String status1) {
		return orderRepository.findOrdersByMerchantIdAndStatus(merchant_id, status, status1);
	}

	@Override
	public void deleteOrder(Long order_id) {
		Order order = orderRepository.findById(order_id);
		order.setDeleteFlag(true);
		orderRepository.update(order);
		
	}

}
