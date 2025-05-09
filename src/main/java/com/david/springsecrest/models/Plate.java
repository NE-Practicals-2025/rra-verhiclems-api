package com.david.springsecrest.models;

import com.david.springsecrest.audits.TimestampAudit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "plates")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Plate extends TimestampAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private String plateNumber;

    @Column(name = "issued_date")
    @NonNull
    private LocalDateTime issuedDate;

    @ManyToOne(targetEntity = Owner.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Owner owner;
}
