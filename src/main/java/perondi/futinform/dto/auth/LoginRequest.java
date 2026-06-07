package perondi.futinform.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@Email
                            @NotBlank(message = "E-mail é obrigatório")
                            String email,

                           @NotBlank(message = "Senha é obrigatória")
                            String password) {
}
