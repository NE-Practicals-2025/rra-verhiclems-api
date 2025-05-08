package com.david.springsecrest.payload.request;


import com.david.springsecrest.enums.ERole;
import com.david.springsecrest.helpers.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterOwnerDTO {

    @NotBlank
    private String names;

    @NotBlank
    private String nationalId;

    @NotBlank
    @Pattern(regexp = "[0-9]{9,12}", message = "Your phone is not a valid tel we expect 2507***, or 07*** or 7***")
    private String telephone;

    @ValidPassword
    private String address;
}