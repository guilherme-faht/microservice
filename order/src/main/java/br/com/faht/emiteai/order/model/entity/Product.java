package br.com.faht.emiteai.order.model.entity;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.faht.emiteai.order.model.dto.ProductDto;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	private String name;
	private Double price;

	public static Product fromProductDto(ProductDto productDto) {
		ModelMapper modelMapper = new ModelMapper();
        Converter<ProductDto, UUID> uuidConverter = new AbstractConverter<>() {
            protected UUID convert(ProductDto source) {
            	return Objects.nonNull(source.getId()) ? UUID.fromString(source.getId()) : null;
            }
        };
        PropertyMap<ProductDto, Product> propertyMap = new PropertyMap<>() {
            protected void configure() {
            	using(uuidConverter).map(source).setId(null);
            }
        };
        modelMapper.addMappings(propertyMap);
				
		return modelMapper.map(productDto, Product.class);
	}
}
