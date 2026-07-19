package perondi.futinform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import perondi.futinform.dtos.match.MatcheResponseDTO;
import perondi.futinform.dtos.standing.StandingResponseDTO;
import perondi.futinform.dtos.user.TeamResponseDTO;
import perondi.futinform.dtos.user.TeamsResponseDTO;
import perondi.futinform.services.FootballService;

@RestController
@RequestMapping("/api/brasileirao")
@RequiredArgsConstructor
public class BrasileiraoController {

    private final FootballService footballService;

    @GetMapping("/standings")
    public ResponseEntity<StandingResponseDTO> getStandings() {
        return ResponseEntity.ok(footballService.getStandings());
    }

    @GetMapping("/matches")
    public ResponseEntity<MatcheResponseDTO> getUpcomingMatches() {
        return ResponseEntity.ok(footballService.getUpcomingMatches());
    }

    @GetMapping("/teams")
    public ResponseEntity<TeamsResponseDTO> getTeams() {
        return ResponseEntity.ok(footballService.getBrasileiraoTeams());
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable Integer id) {
        return ResponseEntity.ok(footballService.getTeamById(id));
    }
}