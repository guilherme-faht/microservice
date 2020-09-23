package br.com.faht.emiteai.order.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faht.emiteai.order.model.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
	
	public List<Order> findAllByTotalGreaterThanEqual(double total);
}
