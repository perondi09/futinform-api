package perondi.futinform.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamResponseDTO {

    private Integer id;
    private String name;
    private String shortName;
    private String tla;
    private String crest;
    private String address;
    private String website;
    private Integer founded;
    private String clubColors;
    private String venue;
    private Area area;
    private Coach coach;
    private List<RunningCompetition> runningCompetitions;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Area {
        private Integer id;
        private String name;
        private String code;
        private String flag;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coach {
        private Integer id;
        private String firstName;
        private String lastName;
        private String name;
        private String dateOfBirth;
        private String nationality;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RunningCompetition {
        private Integer id;
        private String name;
        private String code;
        private String type;
        private String emblem;
    }
}