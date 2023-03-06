package shopbaeFood.service.seviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shopbaeFood.model.Account;
import shopbaeFood.model.Cart;
import shopbaeFood.model.Merchant;
import shopbaeFood.model.Order;
import shopbaeFood.model.OrderDetail;
import shopbaeFood.model.Product;
import shopbaeFood.model.dto.OrderDTO;
import shopbaeFood.repository.ICartRepository;
import shopbaeFood.repository.IOrderDetailRepository;
import shopbaeFood.repository.IOrderRepository;
import shopbaeFood.repository.IProductRepository;
import shopbaeFood.service.IAccountService;
import shopbaeFood.service.IMerchantService;
import shopbaeFood.service.IOrderService;
import shopbaeFood.util.Constants;
import shopbaeFood.util.Page;

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
	private IAccountService accountService;
	
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	private final int PAGE_SIZE = 5;

	@Override
	public boolean checkout(Order order, RedirectAttributes redirectAttributes) {
		Account account = accountService.getAccount();
		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());
		if(carts.isEmpty()) {
			System.out.println("cart null");
			redirectAttributes.addFlashAttribute("mess", "Mời bạn thêm sản phẩm vào giỏ hàng!");
			return true;
		}
		
		order.setAppUser(account.getUser());
		order.setStatus(Constants.ORDER_STATE.PENDING);
		orderRepository.save(order);
		OrderDetail orderDetail = new OrderDetail();
		int quantity;
		for (Cart cart : carts) {
			orderDetail.setProduct(cart.getProduct());
			orderDetail.setQuantity(cart.getQuantity());
			orderDetail.setPrice(cart.getProduct().getNewPrice());
			orderDetail.setOrder(order);
			orderDetail.setDeleteFlag(false);
			orderDetailRepository.save(orderDetail);
			Product product = productRepository.findById(cart.getProduct().getId());
			quantity = product.getQuantity() - cart.getQuantity();
			product.setQuantity(quantity);
			productRepository.update(product);
			cart.setDeleteFlag(true);
			cartRepository.update(cart);
		}
		messagingTemplate.convertAndSend("/topic/"+ order.getMerchant_id(),
		new OrderDTO(order.getId(), 
				account.getUser().getName(),
				order.getStatus(), 
				orderDetail.getProduct().getMerchant().getName()
				));
		return true;

		
	}
	
	@Override
	public List<Product> listProductDelete() {
		Account account = accountService.getAccount();
		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());
		List<Product>listProductDelete = new ArrayList<>();
		for (Cart cart : carts) {
			if(cart.getProduct().isDeleteFlag()) {
				System.out.println("Product đã bị xóa");
				listProductDelete.add(cart.getProduct());
			}
		}
		return listProductDelete;
	}
	
	@Override
	public List<Product> listProductChangePrice() {
		Account account = accountService.getAccount();
		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());
		List<Product>listProductChangePrice = new ArrayList<>();
		for (Cart cart : carts) {
			System.out.println("Tiền product: "+cart.getProduct().getNewPrice());
			System.out.println("Tiền cart: "+cart.getPrice());
			if(!cart.getProduct().getNewPrice().equals(cart.getPrice())){
				listProductChangePrice.add(cart.getProduct());
			}
		}
		return listProductChangePrice;
	}
	
	@Override
	public List<Product> listProductOutOfStock() {
		Account account = accountService.getAccount();
		List<Cart> carts = cartRepository.findAllCartByUserIdAndDeleteFlag(account.getUser().getId());
		List<Product>listProductOutOfStock = new ArrayList<>();
		for (Cart cart : carts) {
			if(cart.getProduct().getQuantity()< 0 || cart.getProduct().getQuantity()< cart.getQuantity()){
				listProductOutOfStock.add(cart.getProduct());
			}
		}
		return listProductOutOfStock;
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
		if(Constants.ORDER_STATE.SELLER_RECEIVE.equals(status)) {
			messagingTemplate.convertAndSend("/topic/"+ order.getAppUser().getId(),
					new OrderDTO(order.getId(), 
							order.getAppUser().getName(),
							status,
							merchant.getName()
					));
		}
		String route = "redirect: /shopbaeFood/merchant/order-manager/seller-receive/1";
		if (Constants.ORDER_STATE.BUYER_RECEIVE.equals(status) || Constants.ORDER_STATE.BUYER_REFUSE.equals(status)) {
			route = "redirect:/user/cart";
		}
		return route;

	}



	@Override
	public void deleteOrder(Long order_id) {
		Order order = orderRepository.findById(order_id);
		order.setUserDeleteFlag(true);
		orderRepository.update(order);

	}

	@Override
	public Page<Order> page(String status, int pageNumber, HttpSession session) {
		Long merchant_id = (Long) session.getAttribute("userId");
		Page<Order> page = new Page<Order>();
		List<Order> orders = null;
		int lastPageNumber = 0;
		if (Constants.ORDER_STATE.PENDING.equals(status) || Constants.ORDER_STATE.SELLER_RECEIVE.equals(status)) {
			orders = page.paging(pageNumber,PAGE_SIZE, orderRepository.findOrdersByMerchantId(merchant_id, status));
			lastPageNumber = page.lastPageNumber(PAGE_SIZE, orderRepository.findOrdersByMerchantId(merchant_id, status));
		}
		
		if (Constants.ORDER_STATE.HISTORY.equals(status)) {
			orders = page.paging(pageNumber,PAGE_SIZE, 
					orderRepository
					.findOrdersByMerchantIdAndStatus(
							merchant_id, 
							Constants.ORDER_STATE.BUYER_RECEIVE, 
							Constants.ORDER_STATE.BUYER_REFUSE));
			
			lastPageNumber =  page.lastPageNumber(PAGE_SIZE,
					orderRepository
					.findOrdersByMerchantIdAndStatus(
							merchant_id, 
							Constants.ORDER_STATE.BUYER_RECEIVE, 
							Constants.ORDER_STATE.BUYER_REFUSE));
			
		}
		
		page.setPaging(orders);
		page.setLastPageNumber(lastPageNumber);
		
		return page;
	}

	

}
