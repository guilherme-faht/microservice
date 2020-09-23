package br.com.faht.emiteai.transport.model.dto;

import java.util.Objects;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.faht.emiteai.transport.model.entity.Product;
import lombok.Data;

@Data
public class ProductDto {
	
	private String id;
	private String name;
	
	public static ProductDto fromProduct(Product product) {
		ModelMapper modelMapper = new ModelMapper();
        Converter<Product, String> uuidConverter = new AbstractConverter<>() {
            protected String convert(Product source) {
                return Objects.nonNull(source.getId()) ? source.getId().toString() : null;
            }
        };
        PropertyMap<Product, ProductDto> propertyMap = new PropertyMap<>() {
            protected void configure() {
            	using(uuidConverter).map(source).setId(null);
            }
        };
        modelMapper.addMappings(propertyMap);
		
		return modelMapper.map(product, ProductDto.class);
	}
}
