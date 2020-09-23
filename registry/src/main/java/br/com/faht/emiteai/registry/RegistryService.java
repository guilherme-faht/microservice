package br.com.faht.emiteai.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegistryService {

	public static void main(String[] args) {
		SpringApplication.run(RegistryService.class, args);
	}

}
