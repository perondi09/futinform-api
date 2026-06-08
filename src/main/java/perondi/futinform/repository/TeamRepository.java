package perondi.futinform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perondi.futinform.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByExternalId(Long externalId);

    List<Team> findByLeagueCodeOrderByNameAsc(String leagueCode);

    List<Team> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
}
