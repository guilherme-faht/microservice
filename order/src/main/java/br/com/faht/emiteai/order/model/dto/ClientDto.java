package br.com.faht.emiteai.order.model.dto;

import java.util.Objects;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.faht.emiteai.order.model.entity.Client;
import lombok.Data;

@Data
public class ClientDto {
	
	private String id;
	private String name;
	private String email;
	
	public static ClientDto fromClient(Client client) {
		ModelMapper modelMapper = new ModelMapper();
        Converter<Client, String> uuidConverter = new AbstractConverter<>() {
            protected String convert(Client source) {
                return Objects.nonNull(source.getId()) ? source.getId().toString() : null;
            }
        };
        PropertyMap<Client, ClientDto> propertyMap = new PropertyMap<>() {
            protected void configure() {
            	using(uuidConverter).map(source).setId(null);
            }
        };
        modelMapper.addMappings(propertyMap);
		
        return modelMapper.map(client, ClientDto.class);
	}
}
