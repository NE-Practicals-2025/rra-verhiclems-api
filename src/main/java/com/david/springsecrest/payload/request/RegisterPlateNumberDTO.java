package com.david.springsecrest.payload.request;


import com.david.springsecrest.helpers.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RegisterPlateNumberDTO {

    @NotBlank
    private String plateNumber;

    @NotBlank
    private UUID ownerId;

    @NotBlank
    private LocalDateTime issuedDate;
}