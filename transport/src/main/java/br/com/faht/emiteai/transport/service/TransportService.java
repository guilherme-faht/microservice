package br.com.faht.emiteai.transport.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.faht.emiteai.transport.model.entity.Transport;
import br.com.faht.emiteai.transport.repository.TransportRepository;

@Service
public class TransportService {

	private final TransportRepository transportRepository;
	
	@Autowired
	public TransportService(TransportRepository transportRepository) {
		this.transportRepository = transportRepository;
	}
	
	public Transport findById(UUID id) {
		Optional<Transport> oTransport = transportRepository.findById(id);
		if (!oTransport.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("A ordem de transporte com id %s não foi encontrada", id.toString()));
		}

		return oTransport.get();
	}
}
