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

    @GetMapping("/dashboard")
    public ResponseEntity<LeagueResponseDTO> getDashboard(
            @AuthenticationPrincipal User user) {

        UserFavorite favorite = userFavoriteRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Nenhum time favorito configurado"));
        String leagueCode = favorite.getTeam().getLeague().getCode();

        return ResponseEntity.ok(leagueService.getLeagueData(leagueCode));
    }
}
