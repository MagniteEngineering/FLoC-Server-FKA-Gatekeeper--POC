package org.gatekeeper.endpoint.model;

import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "session_domain")
public class Session {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id;

    @Column(name = "session_id")
    String sessionId;

    @Column(name = "domain")
    String domain;

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }
}

