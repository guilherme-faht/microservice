package br.com.faht.emiteai.transport.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faht.emiteai.transport.model.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

}
