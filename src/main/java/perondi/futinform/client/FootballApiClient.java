package perondi.futinform.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import perondi.futinform.dto.external.FootballApiMatchesResponseDTO;
import perondi.futinform.dto.external.FootballApiStandingDTO;

@Component
@RequiredArgsConstructor
public class FootballApiClient {

    private final RestClient footballRestClient;

    public FootballApiMatchesResponseDTO fetchMatches(String competitionCode) {
        return footballRestClient
                .get()
                .uri("/competitions/{code}/matches", competitionCode)
                .retrieve()
                .body(FootballApiMatchesResponseDTO.class);
    }

    public FootballApiStandingDTO fetchStandings(String competitionCode) {
        return footballRestClient
                .get()
                .uri("/competitions/{code}/standings", competitionCode)
                .retrieve()
                .body(FootballApiStandingDTO.class);
    }
}
