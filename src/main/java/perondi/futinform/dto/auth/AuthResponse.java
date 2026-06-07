package perondi.futinform.dto.auth;

public record AuthResponse(String token,
                           String userId,
                           String fullName,
                           String email,
                           String favoriteTeamName,    // nome do time para exibir no frontend
                           String favoriteLeagueCode,  // código da liga para redirecionar para a tabela certa
                           long expiresIn   ) {
}
