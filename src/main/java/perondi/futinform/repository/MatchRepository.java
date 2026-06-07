package perondi.futinform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perondi.futinform.entity.Match;
import perondi.futinform.entity.MatchStatus;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByLeagueIdOrderByMatchDateAsc(Long leagueId);

    List<Match> findByHomeTeamIdOrAwayTeamIdOrderByMatchDateAsc(
            Long homeTeamId, Long awayTeamId);

    Optional<Match> findByExternalId(Long externalId);

    List<Match> findByLeagueIdAndStatusInOrderByMatchDateAsc(
            Long leagueId, List<MatchStatus> statuses);

    List<Match> findByLeagueIdAndStatusOrderByMatchDateDesc(
            Long leagueId, MatchStatus status);
}
