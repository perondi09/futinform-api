package perondi.futinform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perondi.futinform.entity.League;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {
    Optional<League> findByCode(String code);
}
