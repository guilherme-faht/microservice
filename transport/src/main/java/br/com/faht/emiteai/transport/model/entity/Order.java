package br.com.faht.emiteai.transport.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.faht.emiteai.transport.model.dto.OrderDto;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_order")
public class Order {

	@Id
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	@Column(name= "order_number", updatable = false)
	private Long orderNumber;
	@ManyToOne(fetch = FetchType.EAGER)
	private Client client;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderProduct> orderProducts = new ArrayList<>();
	private Double total;
	
	public static Order fromOrderDto(OrderDto orderDto) {
		ModelMapper modelMapper = new ModelMapper();
        Converter<OrderDto, UUID> uuidConverter = new AbstractConverter<>() {
            protected UUID convert(OrderDto source) {
                return Objects.nonNull(source.getId()) ? UUID.fromString(source.getId()) : null;
            }
        };
        PropertyMap<OrderDto, Order> propertyMap = new PropertyMap<>() {
            protected void configure() {
            	using(uuidConverter).map(source).setId(null);
            }
        };
        modelMapper.addMappings(propertyMap);
		
		Order order = modelMapper.map(orderDto, Order.class);
		order.setClient(getClient(orderDto));
		order.setOrderProducts(getOrderProduct(orderDto, order));
		
		return order;
	}
	
	private static Client getClient(OrderDto orderDto) {
		Client client = new Client();
		client.setId(UUID.fromString(orderDto.getClientId()));
		
		return client;
	}
	
	private static List<OrderProduct> getOrderProduct(OrderDto orderDto, Order order) {
		return orderDto.getOrderProducts().stream().map(o -> {
			Product product = new Product();
			product.setId(UUID.fromString(o.getProductId()));
			
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setId(UUID.fromString(o.getId()));
			orderProduct.setProduct(product);
			orderProduct.setPrice(o.getPrice());
			orderProduct.setOrder(order);
			
			return orderProduct;
		}).collect(Collectors.toList());
	}
}
