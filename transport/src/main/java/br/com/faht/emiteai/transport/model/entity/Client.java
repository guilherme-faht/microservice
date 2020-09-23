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

import br.com.faht.emiteai.transport.model.dto.ClientDto;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_client")
public class Client {

	@Id
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	private String name;

	public static Client fromClientDto(ClientDto clientDto) {
		ModelMapper modelMapper = new ModelMapper();
        Converter<ClientDto, UUID> uuidConverter = new AbstractConverter<>() {
            protected UUID convert(ClientDto source) {
                return UUID.fromString(source.getId());
            }
        };
        PropertyMap<ClientDto, Client> propertyMap = new PropertyMap<>() {
            protected void configure() {
            	using(uuidConverter).map(source).setId(null);
            }
        };
        modelMapper.addMappings(propertyMap);
		
		return modelMapper.map(clientDto, Client.class);
	}
}
