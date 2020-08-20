package org.gatekeeper.endpoint.service;

import org.gatekeeper.endpoint.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface SessionService extends CrudRepository<Session, Integer> {
}

