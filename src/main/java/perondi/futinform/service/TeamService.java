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
