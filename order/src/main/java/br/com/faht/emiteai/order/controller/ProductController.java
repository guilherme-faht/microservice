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

import br.com.faht.emiteai.order.model.dto.ProductDto;
import br.com.faht.emiteai.order.model.entity.Product;
import br.com.faht.emiteai.order.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private final ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/insert")
	public ResponseEntity<ProductDto> insertProduct(@RequestBody ProductDto productDto) {
		Product product = productService.insertProduct(Product.fromProductDto(productDto));
		
		return ResponseEntity.ok(ProductDto.fromProduct(product));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody ProductDto menuDto) {
		Product product = Product.fromProductDto(menuDto);
		product.setId(UUID.fromString(id));
		Product updated = productService.updateMenu(product);
		
		return ResponseEntity.ok(ProductDto.fromProduct(updated));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> findProduct(@PathVariable String id) {
		Product found = productService.findById(UUID.fromString(id));
		
		return ResponseEntity.ok(ProductDto.fromProduct(found));
	}
}