package com.david.springsecrest.models;

import com.david.springsecrest.audits.InitiatorAudit;
import com.david.springsecrest.audits.TimestampAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "owners")
public class Owner extends TimestampAudit {

    @Id
    private UUID id;

    private String names;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;
}
