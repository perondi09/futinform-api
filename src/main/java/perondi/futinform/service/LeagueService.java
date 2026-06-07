package perondi.futinform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import perondi.futinform.dto.LeagueResponseDTO;
import perondi.futinform.dto.MatchDTO;
import perondi.futinform.dto.StandingDTO;
import perondi.futinform.entity.League;
import perondi.futinform.entity.MatchStatus;
import perondi.futinform.repository.LeagueRepository;
import perondi.futinform.repository.MatchRepository;
import perondi.futinform.repository.StandingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final MatchRepository matchRepository;
    private final StandingRepository standingRepository;

    public LeagueResponseDTO getLeagueData(String code) {

        League league = leagueRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException(
                        "Liga não encontrada. Sincronize os dados primeiro."));

        List<StandingDTO> standings = standingRepository
                .findByLeagueIdOrderByPositionAsc(league.getId())
                .stream()
                .map(s -> new StandingDTO(
                        s.getPosition(),
                        s.getTeam().getName(),
                        s.getTeam().getCrestUrl(),
                        s.getPlayedGames(),
                        s.getPoints(),
                        s.getWon(),
                        s.getDraw(),
                        s.getLost(),
                        s.getGoalsFor(),
                        s.getGoalsAgainst(),
                        s.getGoalDifference()
                ))
                .toList();

        List<MatchDTO> upcoming = matchRepository
                .findByLeagueIdAndStatusInOrderByMatchDateAsc(
                        league.getId(),
                        List.of(MatchStatus.SCHEDULED, MatchStatus.LIVE))
                .stream()
                .map(this::toMatchDTO)
                .toList();

        List<MatchDTO> past = matchRepository
                .findByLeagueIdAndStatusOrderByMatchDateDesc(
                        league.getId(), MatchStatus.FINISHED)
                .stream()
                .map(this::toMatchDTO)
                .toList();

        return new LeagueResponseDTO(
                league.getName(),
                league.getCode(),
                league.getCountry(),
                standings,
                upcoming,
                past
        );
    }

    private MatchDTO toMatchDTO(perondi.futinform.entity.Match match) {
        return new MatchDTO(
                match.getId(),
                match.getHomeTeam().getName(),
                match.getAwayTeam().getName(),
                match.getHomeScore(),
                match.getAwayScore(),
                match.getStatus(),
                match.getMatchDate()
        );
    }
}
