package br.com.faht.emiteai.order.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.faht.emiteai.order.message.OrderSendMessage;
import br.com.faht.emiteai.order.model.dto.OrderDto;
import br.com.faht.emiteai.order.model.entity.Order;
import br.com.faht.emiteai.order.model.entity.OrderProduct;
import br.com.faht.emiteai.order.model.entity.Product;
import br.com.faht.emiteai.order.repository.OrderRepository;
import br.com.faht.emiteai.order.repository.ProductRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final OrderSendMessage orderSendMessage;

	@Autowired
	public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderSendMessage orderSendMessage) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.orderSendMessage = orderSendMessage;
	}

	@Transactional
	public Order insertOrder(Order order) {
		order.getOrderProducts().forEach(o -> {
			Product product = productRepository.findById(o.getProduct().getId()).get();
			o.setPrice(product.getPrice());
			o.setProduct(product);
			o.setOrder(order);
		});

		Double total = order.getOrderProducts().stream().mapToDouble(OrderProduct::getPrice).sum();
		order.setTotal(total);
		
		Order inserted = orderRepository.saveAndFlush(order);
		orderSendMessage.sendMessage(OrderDto.fromOrder(inserted));
		
		return inserted;
	}
	
	public List<Order> findAllByTotalGreaterThanEqual(Double total) {
		return orderRepository.findAllByTotalGreaterThanEqual(total);
	}
}
