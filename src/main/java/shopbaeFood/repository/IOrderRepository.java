package shopbaeFood.repository;

import java.util.List;

import shopbaeFood.model.Order;

public interface IOrderRepository extends IGeneralRepository<Order> {

	List<Order> findOrdersByUserId(Long user_id);

	List<Order> findOrdersByMerchantId(Long merchant_id, String status);

	List<Order> findOrdersByMerchantIdAndStatus(Long merchant_id, String status, String status1);
}
