package br.com.faht.emiteai.order.model.dto;

import org.modelmapper.ModelMapper;

import br.com.faht.emiteai.order.model.entity.OrderProduct;
import lombok.Data;

@Data
public class OrderProductDto {

	private String id;
	private String orderId;
	private String productId;
	private Double price;

	public static OrderProductDto fromOrder(OrderProduct orderProduct) {
		return new ModelMapper().map(orderProduct, OrderProductDto.class);
	}
}
