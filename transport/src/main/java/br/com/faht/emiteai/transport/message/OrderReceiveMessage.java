package br.com.faht.emiteai.transport.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.faht.emiteai.transport.model.dto.OrderDto;
import br.com.faht.emiteai.transport.model.entity.Order;
import br.com.faht.emiteai.transport.model.entity.Transport;
import br.com.faht.emiteai.transport.repository.OrderRepository;
import br.com.faht.emiteai.transport.repository.TransportRepository;

@Component
public class OrderReceiveMessage {

	private final OrderRepository orderRepository;
	private final TransportRepository transportRepository;

	@Autowired
	public OrderReceiveMessage(OrderRepository orderRepository, TransportRepository transportRepository) {
		this.orderRepository = orderRepository;
		this.transportRepository = transportRepository;
	}

	@RabbitListener(queues = { "${order.order.rabbitmq.queue}" })
	public void receiveMessage(@Payload OrderDto orderDto) {
		System.out.println(orderDto);
		//System.out.println(Order.fromOrderDto(orderDto));
		Order inserted = orderRepository.save(Order.fromOrderDto(orderDto));
		
		Transport transport = new Transport();
		transport.setOrder(inserted);
		transportRepository.save(transport);
	}
}
