package br.com.webscraping.services.validation;

import br.com.webscraping.dto.UserUpdateDTO;
import br.com.webscraping.entities.User;
import br.com.webscraping.exceptions.FieldMessage;
import br.com.webscraping.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(UserUpdateDTO objDto, ConstraintValidatorContext context) {
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        if (objDto.getRoles() == null) {
            list.add(new FieldMessage("role", "Role cannot be null"));
        }

        User obj = userRepository.findByEmail(objDto.getEmail());
        if (obj != null && obj.getId() != userId) {
            list.add(new FieldMessage("email", "Email already exists"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}