package org.gatekeeper.endpoint.service;

import org.gatekeeper.endpoint.model.CohortUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CohortUserIdService extends JpaRepository<CohortUserId, Integer> {
}
