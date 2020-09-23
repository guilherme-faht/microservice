package br.com.faht.emiteai.transport.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faht.emiteai.transport.model.dto.TransportDto;
import br.com.faht.emiteai.transport.model.entity.Transport;
import br.com.faht.emiteai.transport.service.TransportService;

@RestController
@RequestMapping("/transport")
public class TransportController {
	
	private final TransportService transportService;
	
	@Autowired
	public TransportController(TransportService transportService) {
		this.transportService = transportService;
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<TransportDto> findTransport(@PathVariable String id) {
		Transport found = transportService.findById(UUID.fromString(id));
		
		return ResponseEntity.ok(TransportDto.fromTransport(found));
	}
}
