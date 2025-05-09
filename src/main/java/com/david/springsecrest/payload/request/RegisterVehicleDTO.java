package com.david.springsecrest.payload.request;


import com.david.springsecrest.helpers.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RegisterVehicleDTO {

    @NotBlank
    private String chassisNumber;

    @NotBlank
    private String manufacturer;

    @NotBlank
    private LocalDateTime manufactureYear;

    @NotBlank
    private Double price;

    @NotBlank
    private String model;

    @NonNull
    private UUID ownerId;

    @NonNull
    private UUID plateNumberId;
}