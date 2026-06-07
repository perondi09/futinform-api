package perondi.futinform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "matches",
        indexes = {
                @Index(name = "idx_match_league_date",
                        columnList = "league_id, match_date"),
                @Index(name = "idx_match_home_team",
                        columnList = "home_team_id, match_date"),
                @Index(name = "idx_match_away_team",
                        columnList = "away_team_id, match_date")
        })

public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    private Integer homeScore;
    private Integer awayScore;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    private LocalDateTime matchDate;
    private Integer matchday;
}
