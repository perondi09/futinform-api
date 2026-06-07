package perondi.futinform.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FootballApiMatchesResponseDTO(List<FootballApiMatchDTO> matches) {
}
