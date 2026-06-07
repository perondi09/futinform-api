package perondi.futinform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import perondi.futinform.dto.MatchDTO;
import perondi.futinform.repository.MatchRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class MatchService {
    private final MatchRepository matchRepository;

    public List<MatchDTO> getMatchesByLeague(Long leagueId) {
        return matchRepository.findByLeagueIdOrderByMatchDateAsc(leagueId)
                .stream()
                .map(match -> new MatchDTO(
                        match.getId(),
                        match.getHomeTeam().getName(),
                        match.getAwayTeam().getName(),
                        match.getHomeScore(),
                        match.getAwayScore(),
                        match.getStatus(),
                        match.getMatchDate()
                ))
                .collect(Collectors.toList());
    }
}
