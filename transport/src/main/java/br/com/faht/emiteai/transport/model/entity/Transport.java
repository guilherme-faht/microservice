package br.com.faht.emiteai.transport.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.faht.emiteai.transport.model.dto.ProductDto;
import br.com.faht.emiteai.transport.model.dto.TransportDto;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_transport")
public class Transport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	@ManyToOne(fetch = FetchType.EAGER)
	private Order order;

	public static Transport fromTransportDto(TransportDto transportDto) {
		ModelMapper modelMapper = new ModelMapper();
        Converter<ProductDto, UUID> uuidConverter = new AbstractConverter<>() {
            protected UUID convert(ProductDto source) {
                return UUID.fromString(source.getId());
            }
        };
        PropertyMap<ProductDto, Transport> propertyMap = new PropertyMap<>() {
            protected void configure() {
            	using(uuidConverter).map(source).setId(null);
            }
        };
        modelMapper.addMappings(propertyMap);
				
		return modelMapper.map(transportDto, Transport.class);
	}
}
