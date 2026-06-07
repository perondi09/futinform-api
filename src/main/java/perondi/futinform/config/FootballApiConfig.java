package perondi.futinform.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration

public class FootballApiConfig {
    @Value("${football.api.url}")
    private String apiUrl;

    @Value("${football.api.key}")
    private String apiKey;

    @Bean
    public RestClient footballRestClient() {
        return RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("X-Auth-Token", apiKey)
                .build();
    }
}
