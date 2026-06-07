package perondi.futinform.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

public record FootballApiMatchDTO(Long id,
                                  FootballApiCompetitionDTO competition,
                                  FootballApiTeamDTO homeTeam,
                                  FootballApiTeamDTO awayTeam,
                                  String status,
                                  Integer matchday,
                                  LocalDateTime utcDate,
                                  FootballApiScoreDTO score) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FootballApiCompetitionDTO(Long id, String name, String code) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FootballApiTeamDTO(Long id, String name, String shortName,
                                     String tla, String crest) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FootballApiScoreDTO(FootballApiFullTimeDTO fullTime) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record FootballApiFullTimeDTO(Integer home, Integer away) {}
}
