package com.david.springsecrest.models;

import com.david.springsecrest.audits.InitiatorAudit;
import com.david.springsecrest.audits.TimestampAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "owners")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Owner extends TimestampAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private String names;

    @Column(name = "national_id", unique = true, nullable = false)
    private String nationalId;

    @Column(name = "phone_number")
    @NonNull
    private String phoneNumber;

    @Column(name = "address")
    @NonNull
    private String address;

    @OneToMany(mappedBy = "owner", targetEntity = Vehicle.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;
}
