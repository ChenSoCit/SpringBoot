package com.example.demoCRUD.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserUpdateRequest {

    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dob;


}
