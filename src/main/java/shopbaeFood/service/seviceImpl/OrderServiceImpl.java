package shopbaeFood.service.seviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import shopbaeFood.model.Account;
import shopbaeFood.model.Cart;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Order;
import shopbaeFood.model.OrderDetail;
import shopbaeFood.model.dto.OrderDTO;
import shopbaeFood.repository.ICartRepository;
import shopbaeFood.repository.IOrderDetailRepository;
import shopbaeFood.repository.IOrderRepository;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.service.IOrderService;
import shopbaeFood.util.Contants;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IOrderDetailRepository orderDetailRepository;

	@Autowired
	private ICartRepository cartRepository;
	
	@Autowired
	private IMerchantService merchantService;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

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
		order.setStatus(Contants.ORDER_STATE.PENDING);
		orderRepository.save(order);

		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());

		OrderDetail orderDetail = new OrderDetail();

		for (Cart cart : carts) {
			orderDetail.setProduct(cart.getProduct());
			orderDetail.setQuantity(cart.getQuantity());
			orderDetail.setOrder(order);
			orderDetail.setDeleteFlag(false);
			orderDetailRepository.save(orderDetail);
			cart.setDeleteFlag(true);
			cartRepository.update(cart);
		}
		
		messagingTemplate.convertAndSend("/topic/"+ order.getMerchant_id(),
			new OrderDTO(order.getId(), 
					account.getUser().getName(),
					order.getStatus(), 
					orderDetail.getProduct().getMerchant().getName()
					));
		
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
		Merchant merchant = merchantService.findById(order.getMerchant_id());
		order.setStatus(status);
		orderRepository.update(order);
		if(Contants.ORDER_STATE.SELLER_RECEIVE.equals(status)) {
			messagingTemplate.convertAndSend("/topic/"+ order.getAppUser().getId(),
					new OrderDTO(order.getId(), 
							order.getAppUser().getName(),
							status,
							merchant.getName()
					));
		}
		String route = "redirect: /shopbaeFood/merchant/order-manager/seller-receive";
		if (Contants.ORDER_STATE.BUYER_RECEIVE.equals(status) || Contants.ORDER_STATE.BUYER_REFUSE.equals(status)) {
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
		order.setUserDeleteFlag(true);
		orderRepository.update(order);

	}

	@Override
	public List<Order> findAllOrderByMerchant_idAndDeleteFlag(Long merchant_id, String status, int pageNumber) {
		return orderRepository.findAllOrderByMerchantAndDeleteFlag(merchant_id, status, pageNumber);
	}

	@Override
	public Long lastPageNumber(Long merchant_id, String status) {
		return orderRepository.lastPageNumber(merchant_id, status);
	}

}
