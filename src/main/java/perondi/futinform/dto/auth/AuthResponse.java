package perondi.futinform.dto.auth;

public record AuthResponse(String token,
                           String userId,
                           String fullName,
                           String email,
                           String favoriteTeamName,
                           String favoriteLeagueCode,
                           long expiresIn   ) {
}
