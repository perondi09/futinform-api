package perondi.futinform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // gera UUID como string
    private String id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;  // NUNCA salve senha em texto puro — o BCrypt faz o hash

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, unique = true)
    private String phone;

    // Um usuário pode ter zero ou um time favorito
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserFavorite favorite;
}
