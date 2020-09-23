package br.com.faht.emiteai.transport.model.dto;

import java.util.Objects;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.faht.emiteai.transport.model.entity.Transport;
import lombok.Data;

@Data
public class TransportDto {
	
	private String id;
	private OrderDto order;
	
	public static TransportDto fromTransport(Transport transport) {
		ModelMapper modelMapper = new ModelMapper();
        Converter<Transport, String> uuidConverter = new AbstractConverter<>() {
            protected String convert(Transport source) {
                return Objects.nonNull(source.getId()) ? source.getId().toString() : null;
            }
        };
        PropertyMap<Transport, TransportDto> propertyMap = new PropertyMap<>() {
            protected void configure() {
            	using(uuidConverter).map(source).setId(null);
            }
        };
        modelMapper.addMappings(propertyMap);
		
        return modelMapper.map(transport, TransportDto.class);
	}
}
