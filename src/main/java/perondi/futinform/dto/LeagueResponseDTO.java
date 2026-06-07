package perondi.futinform.dto;

import java.util.List;

public record LeagueResponseDTO(String leagueName,
                                String leagueCode,
                                String country,
                                List<StandingDTO> standings,
                                List<MatchDTO> upcomingMatches,
                                List<MatchDTO> pastMatches) {
}
