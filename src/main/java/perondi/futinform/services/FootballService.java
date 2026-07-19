package perondi.futinform.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import perondi.futinform.dtos.match.MatcheResponseDTO;
import perondi.futinform.dtos.standing.StandingResponseDTO;
import perondi.futinform.dtos.user.TeamResponseDTO;
import perondi.futinform.dtos.user.TeamsResponseDTO;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class FootballService {

    private final RestClient restClient;

    private static final String BRASILEIRAO_CODE = "BSA";

    @Cacheable("standings")
    public StandingResponseDTO getStandings() {
        log.info("Buscando tabela do Brasileirão na API...");
        return restClient.get()
                .uri("/competitions/{code}/standings", BRASILEIRAO_CODE)
                .retrieve()
                .body(StandingResponseDTO.class);
    }

    @Cacheable("matches")
    public MatcheResponseDTO getUpcomingMatches() {
        log.info("Buscando próximos jogos do Brasileirão na API...");

        String dateFrom = LocalDate.now().toString();
        String dateTo = LocalDate.now().plusDays(30).toString();

        return restClient.get()
                .uri("/competitions/{code}/matches?dateFrom={from}&dateTo={to}&status=SCHEDULED",
                        BRASILEIRAO_CODE, dateFrom, dateTo)
                .retrieve()
                .body(MatcheResponseDTO.class);
    }

    @Cacheable("teams")
    public TeamsResponseDTO getBrasileiraoTeams() {
        log.info("Buscando times do Brasileirão na API...");
        return restClient.get()
                .uri("/competitions/{code}/teams", BRASILEIRAO_CODE)
                .retrieve()
                .body(TeamsResponseDTO.class);
    }

    @Cacheable(value = "team", key = "#id")
    public TeamResponseDTO getTeamById(Integer id) {
        log.info("Buscando time id={} na API...", id);
        return restClient.get()
                .uri("/teams/{id}", id)
                .retrieve()
                .body(TeamResponseDTO.class);
    }
}