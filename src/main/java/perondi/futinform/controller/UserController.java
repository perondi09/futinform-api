package perondi.futinform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perondi.futinform.dto.LeagueResponseDTO;
import perondi.futinform.entity.User;
import perondi.futinform.entity.UserFavorite;
import perondi.futinform.repository.UserFavoriteRepository;
import perondi.futinform.service.LeagueService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFavoriteRepository userFavoriteRepository;
    private final LeagueService leagueService;

    // GET /api/users/dashboard
    // Retorna a tabela e os jogos da liga do time favorito do usuário logado
    // @AuthenticationPrincipal injeta o User que o JwtAuthFilter registrou no contexto
    @GetMapping("/dashboard")
    public ResponseEntity<LeagueResponseDTO> getDashboard(
            @AuthenticationPrincipal User user) {

        // Busca o time favorito do usuário logado
        UserFavorite favorite = userFavoriteRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Nenhum time favorito configurado"));

        // Usa o código da liga do time favorito para buscar os dados
        String leagueCode = favorite.getTeam().getLeague().getCode();

        return ResponseEntity.ok(leagueService.getLeagueData(leagueCode));
    }
}
