package perondi.futinform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import perondi.futinform.dto.LeagueResponseDTO;
import perondi.futinform.service.LeagueService;
import perondi.futinform.service.LeagueSyncService;

@RestController
@RequestMapping("/leagues")
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;
    private final LeagueSyncService leagueSyncService;

    // Endpoint genérico por código — /api/leagues/PL, /api/leagues/CL, etc.
    @GetMapping("/leagues/{code}")
    public ResponseEntity<LeagueResponseDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(leagueService.getLeagueData(code.toUpperCase()));
    }

    // Atalhos com nomes amigáveis — /api/premier-league, /api/champions-league
    @GetMapping("/premier-league")
    public ResponseEntity<LeagueResponseDTO> getPremierLeague() {
        return ResponseEntity.ok(leagueService.getLeagueData("PL"));
    }

    @GetMapping("/la-liga")
    public ResponseEntity<LeagueResponseDTO> getLaLiga() {
        return ResponseEntity.ok(leagueService.getLeagueData("PD"));
    }

    @GetMapping("/bundesliga")
    public ResponseEntity<LeagueResponseDTO> getBundesliga() {
        return ResponseEntity.ok(leagueService.getLeagueData("BL1"));
    }

    @GetMapping("/serie-a")
    public ResponseEntity<LeagueResponseDTO> getSerieA() {
        return ResponseEntity.ok(leagueService.getLeagueData("SA"));
    }

    @GetMapping("/ligue-1")
    public ResponseEntity<LeagueResponseDTO> getLigue1() {
        return ResponseEntity.ok(leagueService.getLeagueData("FL1"));
    }

    @GetMapping("/champions-league")
    public ResponseEntity<LeagueResponseDTO> getChampionsLeague() {
        return ResponseEntity.ok(leagueService.getLeagueData("CL"));
    }

    @GetMapping("/brasileirao")
    public ResponseEntity<LeagueResponseDTO> getBrasileirao() {
        return ResponseEntity.ok(leagueService.getLeagueData("BSA"));
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncAll() {
        leagueSyncService.syncAllLeagues();
        return ResponseEntity.ok("Sincronização concluída!");
    }

    @PostMapping("/sync/{code}")
    public ResponseEntity<String> syncOne(@PathVariable String code) {
        String[] info = switch (code.toUpperCase()) {
            case "PL"  -> new String[]{"Premier League", "England"};
            case "PD"  -> new String[]{"La Liga", "Spain"};
            case "BL1" -> new String[]{"Bundesliga", "Germany"};
            case "SA"  -> new String[]{"Serie A", "Italy"};
            case "FL1" -> new String[]{"Ligue 1", "France"};
            case "CL"  -> new String[]{"Champions League", "Europe"};
            case "BSA" -> new String[]{"Brasileirão Série A", "Brazil"};
            default    -> throw new RuntimeException("Código de liga inválido: " + code);
        };

        leagueSyncService.syncLeague(code.toUpperCase(), info[0], info[1]);
        return ResponseEntity.ok("Liga " + info[0] + " sincronizada!");
    }
}
