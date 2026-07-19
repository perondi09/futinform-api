package perondi.futinform.dtos.user;

import lombok.Data;

@Data
public class UserPatchDTO {
    private String name;
    private String email;
    private String favoriteTeamName;
}