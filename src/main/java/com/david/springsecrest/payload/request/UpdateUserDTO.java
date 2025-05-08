package com.david.springsecrest.payload.request;

import com.david.springsecrest.enums.EGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserDTO {
    @Email
    private String email;

    @NotBlank
    private String names;

    @Pattern(regexp = "[0-9]{12}")
    private String telephone;

    private EGender nationalId;

}
