package shopbaeFood.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import shopbaeFood.model.Order;

public interface IOrderService extends IGeneralService<Order> {
	
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
	 * This method is used to find Order by Merchant and status
	 * @param merchant_id
	 * @param status
	 * @return List<Order>
	 */
	List<Order> findOrdersByMerchant(Long merchant_id, String status);

	/**
	 * This method is used to find Orders by Merchant and two status
	 * @param merchant_id
	 * @param status
	 * @param status1
	 * @return List<Order>
	 */
	List<Order> findOrdersByMerchantIdAndStatus(Long merchant_id, String status, String status1);

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

}
