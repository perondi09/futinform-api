package perondi.futinform.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import perondi.futinform.client.FootballApiClient;
import perondi.futinform.dto.external.FootballApiMatchDTO;
import perondi.futinform.dto.external.FootballApiMatchesResponseDTO;
import perondi.futinform.dto.external.FootballApiStandingDTO;
import perondi.futinform.entity.*;
import perondi.futinform.repository.LeagueRepository;
import perondi.futinform.repository.MatchRepository;
import perondi.futinform.repository.StandingRepository;
import perondi.futinform.repository.TeamRepository;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueSyncService {

    private final FootballApiClient footballApiClient;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final StandingRepository standingRepository;

    private static final Map<String, String[]> SUPPORTED_LEAGUES = Map.of(
            "PL",  new String[]{"Premier League", "England"},
            "PD",  new String[]{"La Liga", "Spain"},
            "BL1", new String[]{"Bundesliga", "Germany"},
            "SA",  new String[]{"Serie A", "Italy"},
            "FL1", new String[]{"Ligue 1", "France"},
            "CL",  new String[]{"Champions League", "Europe"}
    );

    public void syncAllLeagues() {
        SUPPORTED_LEAGUES.forEach((code, info) -> {
            try {
                log.info("Sincronizando liga: {}", info[0]);
                syncLeague(code, info[0], info[1]);
                Thread.sleep(6000);
            } catch (Exception e) {
                log.error("Erro ao sincronizar liga {}: {}", code, e.getMessage());
            }
        });
    }

    @Transactional
    public void syncLeague(String code, String name, String country) {

        League league = leagueRepository.findByCode(code)
                .orElseGet(() -> {
                    League newLeague = new League();
                    newLeague.setCode(code);
                    newLeague.setName(name);
                    newLeague.setCountry(country);
                    return leagueRepository.save(newLeague);
                });

        syncMatches(league, code);

        syncStandings(league, code);
    }

    private void syncMatches(League league, String code) {
        FootballApiMatchesResponseDTO response = footballApiClient.fetchMatches(code);

        if (response == null || response.matches() == null) return;

        for (FootballApiMatchDTO matchDTO : response.matches()) {


            Team homeTeam = findOrCreateTeam(matchDTO.homeTeam(), league);

            Team awayTeam = findOrCreateTeam(matchDTO.awayTeam(), league);

            Match match = matchRepository.findByExternalId(matchDTO.id())
                    .orElseGet(() -> {
                        Match newMatch = new Match();
                        newMatch.setExternalId(matchDTO.id());
                        newMatch.setLeague(league);
                        return newMatch;
                    });

            match.setHomeTeam(homeTeam);
            match.setAwayTeam(awayTeam);
            match.setMatchDate(matchDTO.utcDate());
            match.setMatchday(matchDTO.matchday());
            match.setStatus(mapStatus(matchDTO.status()));

            if (matchDTO.score() != null && matchDTO.score().fullTime() != null) {
                match.setHomeScore(matchDTO.score().fullTime().home());
                match.setAwayScore(matchDTO.score().fullTime().away());
            }

            matchRepository.save(match);
        }
    }

    private void syncStandings(League league, String code) {
        FootballApiStandingDTO response = footballApiClient.fetchStandings(code);

        if (response == null || response.standings() == null) return;
        response.standings().stream()
                .filter(table -> "TOTAL".equals(table.type()))
                .findFirst()
                .ifPresent(table -> {
                    standingRepository.deleteByLeagueId(league.getId());

                    for (var entry : table.table()) {
                        Team team = findOrCreateTeam(entry.team(), league);

                        Standing standing = new Standing();
                        standing.setLeague(league);
                        standing.setTeam(team);
                        standing.setPosition(entry.position());
                        standing.setPlayedGames(entry.playedGames());
                        standing.setPoints(entry.points());
                        standing.setWon(entry.won());
                        standing.setDraw(entry.draw());
                        standing.setLost(entry.lost());
                        standing.setGoalsFor(entry.goalsFor());
                        standing.setGoalsAgainst(entry.goalsAgainst());
                        standing.setGoalDifference(entry.goalDifference());
                        standing.setUpdatedAt(LocalDateTime.now());

                        standingRepository.save(standing);
                    }
                });
    }

    private Team findOrCreateTeam(FootballApiMatchDTO.FootballApiTeamDTO teamDTO,
                                  League league) {
        return teamRepository.findByExternalId(teamDTO.id())
                .orElseGet(() -> {
                    Team team = new Team();
                    team.setExternalId(teamDTO.id());
                    team.setName(teamDTO.name());
                    team.setShortName(teamDTO.shortName());
                    team.setTla(teamDTO.tla());
                    team.setCrestUrl(teamDTO.crest());
                    team.setLeague(league);
                    return teamRepository.save(team);
                });
    }

    private MatchStatus mapStatus(String apiStatus) {
        return switch (apiStatus) {
            case "SCHEDULED", "TIMED" -> MatchStatus.SCHEDULED;
            case "LIVE", "IN_PLAY", "PAUSED" -> MatchStatus.LIVE;
            case "FINISHED" -> MatchStatus.FINISHED;
            case "POSTPONED" -> MatchStatus.POSTPONED;
            case "CANCELLED", "SUSPENDED" -> MatchStatus.CANCELLED;
            default -> MatchStatus.SCHEDULED;
        };
    }
}
