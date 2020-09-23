package br.com.faht.emiteai.transport.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.faht.emiteai.transport.model.dto.ClientDto;
import br.com.faht.emiteai.transport.model.entity.Client;
import br.com.faht.emiteai.transport.repository.ClientRepository;

@Component
public class ClientReceiveMessage {

	private final ClientRepository clientRepository;

	@Autowired
	public ClientReceiveMessage(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@RabbitListener(queues = { "${order.client.rabbitmq.queue}" })
	public void receiveMessage(@Payload ClientDto clientDto) {
		clientRepository.save(Client.fromClientDto(clientDto));
	}
}
