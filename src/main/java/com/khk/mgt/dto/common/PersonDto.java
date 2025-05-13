package com.khk.mgt.dto.common;

import com.khk.mgt.validator.annotations.UniqueMail;
import com.khk.mgt.validator.annotations.UniquePhone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class PersonDto {

    private Long id;

    @NotBlank(message = "FirstName must not blank.")
    private String firstName;

    @NotBlank(message = "LastName must not blank.")
    private String lastName;

    @NotBlank(message = "Gender must not blank.")
    private String gender;

    @NotNull(message = "Date OF Birth must not blank.")
    @Past
    private Date dateOfBirth;

    @Email(message = "Invalid email address.")
    @NotBlank(message = "Email must not blank.")
    @UniqueMail
    private String email;

    @NotBlank(message = "Phone must not blank.")
    @UniquePhone
    private String phone;

    @Valid
    @NotNull
    private AddressDto address;

    public PersonDto() {
    }
}
