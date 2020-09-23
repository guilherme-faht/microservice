package br.com.faht.emiteai.transport.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.faht.emiteai.transport.model.dto.ProductDto;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_product")
public class Product {

	@Id
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	private String name;

	public static Product fromProductDto(ProductDto productDto) {
		ModelMapper modelMapper = new ModelMapper();
        Converter<ProductDto, UUID> uuidConverter = new AbstractConverter<>() {
            protected UUID convert(ProductDto source) {
                return UUID.fromString(source.getId());
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
