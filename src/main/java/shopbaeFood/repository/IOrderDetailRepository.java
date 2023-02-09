package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.OrderDetail;

public interface IOrderDetailRepository extends IGeneralRepository<OrderDetail> {
	List<OrderDetail> findOrderDetailsByOrderId(Long order_id);
}
