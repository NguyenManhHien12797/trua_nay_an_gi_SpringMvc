package trua_nay_an_gi.service;


import java.util.List;

import trua_nay_an_gi.model.OrderDetail;

public interface IOrderDetailService extends IGeneralService<OrderDetail>{
	List<OrderDetail> findOrderDetailsByOrderId(Long order_id);
}
