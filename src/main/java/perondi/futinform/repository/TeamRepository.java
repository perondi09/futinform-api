package perondi.futinform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perondi.futinform.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByExternalId(Long externalId);

    // Busca todos os times de uma liga pelo código
    List<Team> findByLeagueCodeOrderByNameAsc(String leagueCode);

    // Busca times pelo nome (para o usuário pesquisar)
    List<Team> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
}
