package ru.shakurov.spring_webapp.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpForm {
    @Email(message = "{error.incorrect.email}")
    private String email;

    private String password;

    private String username;

    @NotBlank(message = "{error.null.alias}")
    private String alias;
}
