package trua_nay_an_gi.repository;

import java.util.List;

import trua_nay_an_gi.model.OrderDetail;

public interface IOrderDetailRepository extends IGeneralRepository<OrderDetail>{
//	List<OrderDetail> findOrderDetailsByMerchantId(Long merchant_id, String status);
//	
	List<OrderDetail> findOrderDetailsByOrderId(Long order_id);
}
