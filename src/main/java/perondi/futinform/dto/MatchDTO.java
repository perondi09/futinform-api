package perondi.futinform.dto;

import perondi.futinform.entity.MatchStatus;

import java.time.LocalDateTime;

public record MatchDTO(Long id,
                       String homeTeamName,
                       String awayTeamName,
                       Integer homeScore,
                       Integer awayScore,
                       MatchStatus status,
                       LocalDateTime matchDate) {
}
