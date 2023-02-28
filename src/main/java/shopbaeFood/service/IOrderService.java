package shopbaeFood.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import shopbaeFood.model.Merchant;
import shopbaeFood.model.Order;
import shopbaeFood.util.Page;

public interface IOrderService{
	
	/**
	 * This method is used to checkout 
	 * @param order
	 * @param session
	 */
	void checkout(Order order, HttpSession session);
	
	/**
	 * This method is used to update Order status
	 * @param order_id
	 * @param status
	 * @return view
	 */
	String updateOrderStatus(Long order_id, String status);

	/**
	 * This method is used to find Orders by user_id
	 * @param user_id
	 * @return List<Order.
	 */
	List<Order> findOrdersByUserId(Long user_id);
	
	/**
	 * This method is used to delete Order
	 * @param order_id
	 */
	void deleteOrder(Long order_id);
	/**
	 * This method is used to paging
	 * @param status
	 * @param pageNumber
	 * @param session
	 * @return
	 */
	Page<Order> page(String status, int pageNumber, HttpSession session);

}
