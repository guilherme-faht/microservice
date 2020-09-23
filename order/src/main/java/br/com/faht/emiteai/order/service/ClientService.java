package br.com.faht.emiteai.order.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.faht.emiteai.order.message.ClientSendMessage;
import br.com.faht.emiteai.order.model.dto.ClientDto;
import br.com.faht.emiteai.order.model.entity.Client;
import br.com.faht.emiteai.order.repository.ClientRepository;

@Service
public class ClientService {

	private final ClientRepository clientRepository;
	private final ClientSendMessage clientSendMessage;

	@Autowired
	public ClientService(ClientRepository clientRepository, ClientSendMessage clientSendMessage) {
		this.clientRepository = clientRepository;
		this.clientSendMessage = clientSendMessage;
	}

	public Client insertClient(Client client) {
		Client inserted = clientRepository.save(client);
		clientSendMessage.sendMessage(ClientDto.fromClient(inserted));

		return inserted;
	}

	public Client updateClient(Client client) {
		Optional<Client> oClient = clientRepository.findById(client.getId());
		if (!oClient.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("O cliente com id %s não foi encontrado", client.getId().toString()));
		}

		Client updated = clientRepository.save(client);
		clientSendMessage.sendMessage(ClientDto.fromClient(updated));

		return updated;
	}

	public Client findById(UUID id) {
		Optional<Client> oClient = clientRepository.findById(id);
		if (!oClient.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("O cliente com id %s não foi encontrado", id.toString()));
		}

		return oClient.get();
	}
}
