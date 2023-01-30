package trua_nay_an_gi.service.seviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trua_nay_an_gi.model.Order;
import trua_nay_an_gi.repository.IOrderRepository;
import trua_nay_an_gi.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService{
	
	@Autowired
	private IOrderRepository orderRepository;

	@Override
	public Order findById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public void save(Order order) {
		orderRepository.save(order);
		
	}

	@Override
	public void update(Order order) {
		orderRepository.update(order);
		
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

}
