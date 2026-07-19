package perondi.futinform.dtos.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatcheResponseDTO {

    private List<Match> matches;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Match {
        private Integer id;
        private String utcDate;
        private String status;
        private Integer matchday;
        private MatchTeam homeTeam;
        private MatchTeam awayTeam;
        private Score score;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MatchTeam {
        private Integer id;
        private String name;
        private String shortName;
        private String crest;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Score {
        private String winner;
        private HalfScore fullTime;
        private HalfScore halfTime;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HalfScore {
        private Integer home;
        private Integer away;
    }
}