package com.nrqapps.j2ee.models;

import javax.persistence.*;

/**
 * Created by Mauricio Enriquez on 8/20/17.
 * NrqApps © 2017
 */
@Entity
@Table(name = "marital_status")
public class MaritalStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marital_status_id")
    private Integer maritalStatusId;

    private String status;

    public Integer getMaritalStatusId() {
        return maritalStatusId;
    }

    public void setMaritalStatusId(Integer maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
