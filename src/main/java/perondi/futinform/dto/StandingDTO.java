package perondi.futinform.dto;

public record StandingDTO (Integer position,
                           String teamName,
                           String teamCrest,
                           Integer playedGames,
                           Integer points,
                           Integer won,
                           Integer draw,
                           Integer lost,
                           Integer goalsFor,
                           Integer goalsAgainst,
                           Integer goalDifference){
}
