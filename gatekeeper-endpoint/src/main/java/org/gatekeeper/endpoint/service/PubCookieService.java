package org.gatekeeper.endpoint.service;

import org.gatekeeper.endpoint.model.PubCookie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubCookieService extends JpaRepository<PubCookie, Integer> {
}
