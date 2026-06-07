package perondi.futinform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import perondi.futinform.dto.TeamDTO;
import perondi.futinform.repository.TeamRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    // Lista todos os times de uma liga específica
    // Usado na tela de cadastro quando o usuário filtra por liga
    public List<TeamDTO> getTeamsByLeague(String leagueCode) {
        return teamRepository.findByLeagueCodeOrderByNameAsc(leagueCode)
                .stream()
                .map(team -> new TeamDTO(
                        team.getId(),
                        team.getName(),
                        team.getShortName(),
                        team.getTla(),
                        team.getCrestUrl(),
                        team.getLeague() != null ? team.getLeague().getName() : null,
                        team.getLeague() != null ? team.getLeague().getCode() : null
                ))
                .toList();
    }

    // Lista todos os times de todas as ligas
    // Útil para o usuário navegar sem filtro
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(team -> new TeamDTO(
                        team.getId(),
                        team.getName(),
                        team.getShortName(),
                        team.getTla(),
                        team.getCrestUrl(),
                        team.getLeague() != null ? team.getLeague().getName() : null,
                        team.getLeague() != null ? team.getLeague().getCode() : null
                ))
                .toList();
    }

    // Busca times pelo nome — para campo de busca no frontend
    public List<TeamDTO> searchTeams(String name) {
        return teamRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name)
                .stream()
                .map(team -> new TeamDTO(
                        team.getId(),
                        team.getName(),
                        team.getShortName(),
                        team.getTla(),
                        team.getCrestUrl(),
                        team.getLeague() != null ? team.getLeague().getName() : null,
                        team.getLeague() != null ? team.getLeague().getCode() : null
                ))
                .toList();
    }
}
