package br.com.faht.emiteai.order.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.faht.emiteai.order.model.dto.ProductDto;

@Component
public class ProductSendMessage {
	
	private String exchange;

	private String routingKey;
	
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	public ProductSendMessage(@Value("${order.rabbitmq.exchange}") String exchange, @Value("${order.product.rabbitmq.routingkey}") String routingKey, RabbitTemplate rabbitTemplate) {
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendMessage(ProductDto productDto) {
		rabbitTemplate.convertAndSend(exchange, routingKey, productDto);
	}
}
