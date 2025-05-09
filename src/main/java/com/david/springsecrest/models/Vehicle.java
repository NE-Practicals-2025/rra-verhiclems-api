package com.david.springsecrest.models;

import com.david.springsecrest.audits.InitiatorAudit;
import com.david.springsecrest.audits.TimestampAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vehicle extends TimestampAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "manufacturer", unique = true, nullable = false)
    private String manufacturer;

    @Column(name = "manufacture_year")
    @NonNull
    private LocalDateTime manufactureYear;

    @Column(name = "model")
    @NonNull
    private String Model;

    @ManyToOne
    private Owner owner;
}
