package perondi.futinform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import perondi.futinform.dto.TeamDTO;
import perondi.futinform.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    // Lista todos os times de uma liga
    // GET /teams/league/PL  →  times da Premier League
    // GET /teams/league/BSA →  times do Brasileirão
    @GetMapping("/league/{leagueCode}")
    public ResponseEntity<List<TeamDTO>> getByLeague(
            @PathVariable String leagueCode) {
        return ResponseEntity.ok(
                teamService.getTeamsByLeague(leagueCode.toUpperCase()));
    }

    // Lista todos os times de todas as ligas
    // GET /teams
    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAll() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    // Busca times pelo nome
    // GET /teams/search?name=manchester
    @GetMapping("/search")
    public ResponseEntity<List<TeamDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(teamService.searchTeams(name));
    }
}
