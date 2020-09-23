package br.com.faht.emiteai.order.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import br.com.faht.emiteai.order.model.dto.OrderProductDto;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_order_product")
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
	private Order order;	
	@ManyToOne(fetch = FetchType.EAGER)
	private Product product;
	private Double price;
	
	public static OrderProduct fromOrderDto(OrderProductDto orderProductDto) {
		return new ModelMapper().map(orderProductDto, OrderProduct.class);
	}
}
