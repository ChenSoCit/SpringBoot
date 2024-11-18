package com.example.demoCRUD.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {
    @Size(min = 3, message = "USERNAME_VALIDAL")
    private String username;
    @Size(min = 8, message = "PASSWORD_VALIDAL")
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;


}
