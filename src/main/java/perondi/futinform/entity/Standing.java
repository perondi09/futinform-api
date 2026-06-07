package perondi.futinform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "standings",
        indexes = {
                // Índice composto: acelera busca por liga ordenada por posição
                @Index(name = "idx_standing_league_position",
                        columnList = "league_id, position")
        })

public class Standing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private Integer position;
    private Integer playedGames;
    private Integer points;
    private Integer won;
    private Integer draw;
    private Integer lost;
    private Integer goalsFor;
    private Integer goalsAgainst;
    private Integer goalDifference;

    private LocalDateTime updatedAt;
}
