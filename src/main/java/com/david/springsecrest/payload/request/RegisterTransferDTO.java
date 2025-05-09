package com.david.springsecrest.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RegisterTransferDTO {

    @NotBlank
    private UUID vehicleId;

    @NotBlank
    private UUID formerOwnerId;

    @NotBlank
    private UUID newOwnerId;

    @NotBlank
    private Double price;
}