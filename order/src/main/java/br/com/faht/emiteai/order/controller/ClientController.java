package br.com.faht.emiteai.order.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faht.emiteai.order.model.dto.ClientDto;
import br.com.faht.emiteai.order.model.entity.Client;
import br.com.faht.emiteai.order.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	private final ClientService clientService;
	
	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@PostMapping("/insert")
	public ResponseEntity<Object> insertClient(@RequestBody ClientDto clientDto) {
		Client client = clientService.insertClient(Client.fromClientDto(clientDto));
		
		return ResponseEntity.ok(client);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateClient(@PathVariable String id, @RequestBody ClientDto clientDto) {
		Client client = Client.fromClientDto(clientDto);
		client.setId(UUID.fromString(id));
		Client updatedClient = clientService.updateClient(client);
		
		return ResponseEntity.ok(updatedClient);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findClient(@PathVariable String id) {
		return ResponseEntity.ok(clientService.findById(UUID.fromString(id)));
	}
}