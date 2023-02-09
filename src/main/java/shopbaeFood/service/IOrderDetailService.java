package shopbaeFood.service;

import java.util.List;

import shopbaeFood.model.OrderDetail;

public interface IOrderDetailService extends IGeneralService<OrderDetail> {
	List<OrderDetail> findOrderDetailsByOrderId(Long order_id);
}
