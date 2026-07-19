package perondi.futinform.dtos.standing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingResponseDTO {

    private Competition competition;
    private Season season;
    private List<StandingGroup> standings;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Competition {
        private Integer id;
        private String name;
        private String code;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Season {
        private Integer id;
        private String startDate;
        private String endDate;
        private Integer currentMatchday;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StandingGroup {
        private String stage;
        private String type;
        private List<TableEntry> table;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TableEntry {
        private Integer position;
        private Team team;
        private Integer playedGames;
        private String form;
        private Integer won;
        private Integer draw;
        private Integer lost;
        private Integer points;
        private Integer goalsFor;
        private Integer goalsAgainst;
        private Integer goalDifference;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Team {
        private Integer id;
        private String name;
        private String shortName;
        private String tla;
        private String crest;
    }
}
