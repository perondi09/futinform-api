package perondi.futinform.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FootballApiStandingDTO(
        List<FootballApiTableDTO> standings
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FootballApiTableDTO(
            String type,
            List<FootballApiStandingEntryDTO> table
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FootballApiStandingEntryDTO(
            Integer position,
            FootballApiMatchDTO.FootballApiTeamDTO team,
            Integer playedGames,
            Integer won,
            Integer draw,
            Integer lost,
            Integer points,
            Integer goalsFor,
            Integer goalsAgainst,
            Integer goalDifference
    ) {}
}