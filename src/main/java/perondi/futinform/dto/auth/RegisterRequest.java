package perondi.futinform.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank(message = "Nome é obrigatório")
                              String fullName,

                              @Email(message = "E-mail inválido")
                              @NotBlank(message = "E-mail é obrigatório")
                              String email,

                              @NotBlank(message = "Senha é obrigatória")
                              @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
                              String password,

                              @NotBlank(message = "Telefone é obrigatório")
                              String phone,

                              // ID do time favorito — o usuário vai escolher na tela de cadastro
                              @NotNull(message = "Escolha um time favorito")
                              Long favoriteTeamId) {
}
