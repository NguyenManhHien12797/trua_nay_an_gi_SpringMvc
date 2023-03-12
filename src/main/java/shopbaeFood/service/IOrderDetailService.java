package shopbaeFood.service;

import java.util.List;

import shopbaeFood.model.OrderDetail;

public interface IOrderDetailService extends IGeneralService<OrderDetail> {
	/**
	 * This method is used to find OrderDetails by OrderId
	 * 
	 * @param order_id
	 * @return List<OrderDetail>
	 */
	List<OrderDetail> findOrderDetailsByOrderId(Long order_id);
}
