package br.com.faht.emiteai.order.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.faht.emiteai.order.model.dto.OrderDto;
import br.com.faht.emiteai.order.model.entity.Event;
import br.com.faht.emiteai.order.repository.EventRepository;

@Component
public class OrderSendMessage {

	private String exchange;

	private String routingKey;

	private RabbitTemplate rabbitTemplate;

	private EventRepository eventRepository;

	@Autowired
	public OrderSendMessage(@Value("${order.rabbitmq.exchange}") String exchange,
			@Value("${order.order.rabbitmq.routingkey}") String routingKey, RabbitTemplate rabbitTemplate,
			EventRepository eventRepository) {
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.rabbitTemplate = rabbitTemplate;
		this.eventRepository = eventRepository;
	}

	public void sendMessage(OrderDto orderDto) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			Event event = new Event();
			event.setType("Order");
			event.setEventData(objectMapper.writeValueAsString(orderDto));
			eventRepository.save(event);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}

		rabbitTemplate.convertAndSend(exchange, routingKey, orderDto);
	}
}
