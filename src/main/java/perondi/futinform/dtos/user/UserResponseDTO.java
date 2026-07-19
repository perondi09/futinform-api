package perondi.futinform.dtos.user;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private Integer favoriteTeamId;
    private String favoriteTeamName;
    private LocalDateTime createdAt;
}
