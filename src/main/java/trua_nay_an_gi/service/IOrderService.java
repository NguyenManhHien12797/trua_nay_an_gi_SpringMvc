package trua_nay_an_gi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import trua_nay_an_gi.model.Order;

public interface IOrderService extends IGeneralService<Order>{
	void checkout(Order order,HttpSession session);
	
	String updateOrderStatus(Long order_id, String status);
	
	List<Order> findOrdersByMerchant(Long merchant_id, String status);
	
	List<Order> findOrdersByMerchantIdAndStatus(Long merchant_id, String status, String status1);
	
	List<Order> findOrdersByUserId(Long user_id);
	
	void deleteOrder(Long order_id);

}
