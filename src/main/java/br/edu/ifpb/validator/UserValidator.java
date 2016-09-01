package br.edu.ifpb.validator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by susanneferraz on 01/09/16.
 */
public class UserValidator {


    @Size(min = 1, max = 150, message = "Não pode ser nulo. Valor máximo - 150 caracteres")
    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = "Insira um email válido")
    private String email;


    @Size(min = 6, max = 150, message = "Senha deve ter no mínimo 6 caracteres")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
