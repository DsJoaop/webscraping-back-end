package br.com.webscraping.dto;


import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    @Email(message = "Please enter a valid email address")
    private String email;

    List<RoleDTO> roles;
}
