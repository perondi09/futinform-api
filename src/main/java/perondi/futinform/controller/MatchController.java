package perondi.futinform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perondi.futinform.dto.MatchDTO;
import perondi.futinform.service.MatchService;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor

public class MatchController {
    private final MatchService matchService;

    @GetMapping("/league/{leagueId}")
    public ResponseEntity<List<MatchDTO>> getMatchesByLeague(@PathVariable Long leagueId) {
        return ResponseEntity.ok(matchService.getMatchesByLeague(leagueId));
    }
}
