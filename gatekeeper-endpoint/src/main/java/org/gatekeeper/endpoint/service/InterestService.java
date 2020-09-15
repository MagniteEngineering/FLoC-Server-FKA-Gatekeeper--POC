package org.gatekeeper.endpoint.service;

import java.util.List;
import org.gatekeeper.endpoint.model.Cohort;
import org.gatekeeper.endpoint.model.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestService extends JpaRepository<Interest, Integer> {

    @Query("SELECT t FROM Interest t where t.sessionId = ?1 order by created_on desc")
    List<Interest> findBySessionId(String sessionId);


}

