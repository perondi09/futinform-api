package perondi.futinform.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String shortName;
    private String tla;       // sigla de 3 letras, ex: "MCI"
    private String crestUrl;  // URL do escudo do time

    // Muitos teams pertencem a um league
    @ManyToOne(fetch = FetchType.LAZY)  // LAZY = só busca a liga quando você pedir
    @JoinColumn(name = "league_id")     // nome da coluna FK no banco
    private League league;
}