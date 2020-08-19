package org.gatekeeper.endpoint.model;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PUB_COOKIE")
public class PubCookie {

    @Column(name = "ID")
    @Id
    Integer id;

    @Column(name = "PUB_ID")
    String pubid;

    @Column(name = "COOKIE")
    String cookie;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
