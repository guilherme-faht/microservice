package br.com.faht.emiteai.order.model.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import br.com.faht.emiteai.order.model.entity.Order;
import lombok.Data;

@Data
public class OrderDto {

	private String id;
	private Long orderNumber;
	@NotEmpty
	private String clientId;
	@Size(min = 1, max = 3)
	private List<OrderProductDto> orderProducts;
	private Double total;

	public static OrderDto fromOrder(Order order) {
		return new ModelMapper().map(order, OrderDto.class);
	}
}
