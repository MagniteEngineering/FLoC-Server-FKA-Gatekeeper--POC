package org.gatekeeper.endpoint.service;

import org.gatekeeper.endpoint.model.Interest;
import org.springframework.data.repository.CrudRepository;


public interface InterestService extends CrudRepository<Interest, Integer> {
}

