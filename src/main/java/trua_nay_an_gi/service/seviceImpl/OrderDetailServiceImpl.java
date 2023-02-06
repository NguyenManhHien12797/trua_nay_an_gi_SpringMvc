package trua_nay_an_gi.service.seviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trua_nay_an_gi.model.OrderDetail;
import trua_nay_an_gi.repository.IOrderDetailRepository;
import trua_nay_an_gi.service.IOrderDetailService;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService{
	
	@Autowired
	private IOrderDetailRepository orderDetailRepository;
	
	@Override
	public OrderDetail findById(Long id) {
		return orderDetailRepository.findById(id);
	}

	@Override
	public void save(OrderDetail orderDetail) {
		orderDetailRepository.save(orderDetail);
		
	}

	@Override
	public void update(OrderDetail orderDetail) {
		orderDetailRepository.update(orderDetail);
		
	}

	@Override
	public List<OrderDetail> findAll() {
		return orderDetailRepository.findAll();
	}

	@Override
	public List<OrderDetail> findOrderDetailsByOrderId(Long order_id) {
		return orderDetailRepository.findOrderDetailsByOrderId(order_id);
	}



}
