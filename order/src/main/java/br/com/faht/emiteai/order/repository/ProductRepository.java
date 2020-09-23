package br.com.faht.emiteai.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faht.emiteai.order.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
