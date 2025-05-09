package com.david.springsecrest.models;

import com.david.springsecrest.audits.InitiatorAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends InitiatorAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double price;

    @OneToOne(targetEntity = Owner.class, fetch = FetchType.EAGER)
    private Owner formerOwner;

    @OneToOne(targetEntity = Owner.class, fetch = FetchType.EAGER)
    private Owner newOwner;

    @OneToOne(targetEntity = Vehicle.class, fetch = FetchType.EAGER)
    private Vehicle vehicle;

}
