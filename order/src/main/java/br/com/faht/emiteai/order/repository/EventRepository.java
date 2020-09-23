package br.com.faht.emiteai.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faht.emiteai.order.model.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

}
