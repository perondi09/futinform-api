package perondi.futinform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perondi.futinform.entity.Standing;

import java.util.List;

public interface StandingRepository extends JpaRepository<Standing, Long> {
    List<Standing> findByLeagueIdOrderByPositionAsc(Long leagueId);
    void deleteByLeagueId(Long leagueId);
}
