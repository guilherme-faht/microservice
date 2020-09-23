package br.com.faht.emiteai.transport.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.faht.emiteai.transport.model.dto.ProductDto;
import br.com.faht.emiteai.transport.model.entity.Product;
import br.com.faht.emiteai.transport.repository.ProductRepository;

@Component
public class ProductReceiveMessage {

	private final ProductRepository productRepository;

	@Autowired
	public ProductReceiveMessage(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@RabbitListener(queues = { "${order.product.rabbitmq.queue}" })
	public void receiveMessage(@Payload ProductDto productDto) {
		productRepository.save(Product.fromProductDto(productDto));
	}
}
