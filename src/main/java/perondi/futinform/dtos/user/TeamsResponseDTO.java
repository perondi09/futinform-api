package perondi.futinform.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamsResponseDTO {

    private List<TeamResponseDTO> teams;
}
