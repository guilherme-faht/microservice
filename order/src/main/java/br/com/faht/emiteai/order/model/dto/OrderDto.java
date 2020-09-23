package br.com.faht.emiteai.order.model.dto;

import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.faht.emiteai.order.model.entity.Order;
import lombok.Data;

@Data
public class OrderDto {

	private String id;
	private Long orderNumber;
	private String clientId;
	private List<OrderProductDto> orderProducts;
	private Double total;

	public static OrderDto fromOrder(Order order) {
		return new ModelMapper().map(order, OrderDto.class);
	}
}
