package org.gatekeeper.endpoint.model;

import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "session_cohort")
public class Cohort {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id;

    @Column(name = "session_id")
    String sessionId;

    @Column(name = "cohort_id")
    String cohortId;

    @Column(name = "created_on")
    Timestamp createdOn;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCohortId() {
        return cohortId;
    }

    public void setCohortId(String cohortId) {
        this.cohortId = cohortId;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }
}

