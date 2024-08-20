package br.com.webscraping.dto;

import br.com.webscraping.services.validation.UserInsertValid;
import br.com.webscraping.services.validation.UserUpdateValid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@UserUpdateValid
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserUpdateDTO extends UserDTO {
}
