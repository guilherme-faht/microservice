package br.com.faht.emiteai.order.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.faht.emiteai.order.message.ProductSendMessage;
import br.com.faht.emiteai.order.model.dto.ProductDto;
import br.com.faht.emiteai.order.model.entity.Product;
import br.com.faht.emiteai.order.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductSendMessage productSendMessage;

	@Autowired
	public ProductService(ProductRepository productRepository, ProductSendMessage productSendMessage) {
		this.productRepository = productRepository;
		this.productSendMessage = productSendMessage;
	}

	public Product insertProduct(Product product) {
		Product inserted = productRepository.save(product);
		productSendMessage.sendMessage(ProductDto.fromProduct(inserted));

		return inserted;
	}

	public Product updateMenu(Product product) {
		Optional<Product> oProduct = productRepository.findById(product.getId());
		if (!oProduct.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("O produto com id %s não foi encontrado", product.getId().toString()));
		}

		return productRepository.save(product);
	}

	public Product findById(UUID id) {
		Optional<Product> oProduct = productRepository.findById(id);
		if (!oProduct.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("O produto com id %s não foi encontrado", id.toString()));
		}

		return oProduct.get();
	}
}
