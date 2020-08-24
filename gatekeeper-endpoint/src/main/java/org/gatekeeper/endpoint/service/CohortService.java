package org.gatekeeper.endpoint.service;

import org.gatekeeper.endpoint.model.Cohort;
import org.gatekeeper.endpoint.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CohortService extends CrudRepository<Cohort, Integer> {

    //@Query("SELECT t.cohort_id FROM session_cohort t where t.session_id = :sid")
    Cohort findBySessionId(String sessionId);
}

