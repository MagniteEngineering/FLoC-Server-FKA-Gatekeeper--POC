package org.gatekeeper.endpoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "session_domain")
public class CohortUserId {

    @Column(name = "ID")
    @Id
    Integer id;

    @Column(name = "session_id")
    String sessionId;

    @Column(name = "domain")
    String domain;

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
}
