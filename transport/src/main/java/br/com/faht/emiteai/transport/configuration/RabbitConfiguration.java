package br.com.faht.emiteai.transport.configuration;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

	@Value("${order.rabbitmq.exchange}")
	private String exchangeName;

	@Value("${order.client.rabbitmq.queue}")
	private String orderClientQueue;

	@Value("${order.product.rabbitmq.queue}")
	private String orderProductQueue;

	@Value("${order.order.rabbitmq.queue}")
	private String orderOrderQueue;

	@Value("${order.client.rabbitmq.routingkey}")
	private String orderClientRoutingkey;

	@Value("${order.product.rabbitmq.routingkey}")
	private String orderProductRoutingkey;

	@Value("${order.order.rabbitmq.routingkey}")
	private String orderOrderRoutingkey;

	@Bean
	@Autowired
	public RabbitAdmin createAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.afterPropertiesSet();

		Exchange exchange = ExchangeBuilder.directExchange(exchangeName).durable(true).build();
		rabbitAdmin.declareExchange(exchange);

		Queue clientQueue = QueueBuilder.durable(orderClientQueue).build();
		rabbitAdmin.declareQueue(clientQueue);
		rabbitAdmin.declareBinding(BindingBuilder.bind(clientQueue).to(exchange).with(orderClientRoutingkey).noargs());

		Queue productQueue = QueueBuilder.durable(orderProductQueue).build();
		rabbitAdmin.declareQueue(productQueue);
		rabbitAdmin.declareBinding(BindingBuilder.bind(productQueue).to(exchange).with(orderProductRoutingkey).noargs());

		Queue orderQueue = QueueBuilder.durable(orderOrderQueue).build();
		rabbitAdmin.declareQueue(orderQueue);
		rabbitAdmin.declareBinding(BindingBuilder.bind(orderQueue).to(exchange).with(orderOrderRoutingkey).noargs());

		return rabbitAdmin;
	}

	@Bean
	@Autowired
	public RabbitTemplate createTemplate(ConnectionFactory connectionFactory) {
		final var rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(createMessageConverter());

		return rabbitTemplate;
	}

	@Bean
	public MessageConverter createMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
