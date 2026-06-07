package perondi.futinform.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import perondi.futinform.dto.auth.AuthResponse;
import perondi.futinform.dto.auth.LoginRequest;
import perondi.futinform.dto.auth.RegisterRequest;
import perondi.futinform.entity.Team;
import perondi.futinform.entity.User;
import perondi.futinform.entity.UserFavorite;
import perondi.futinform.repository.TeamRepository;
import perondi.futinform.repository.UserFavoriteRepository;
import perondi.futinform.repository.UserRepository;
import perondi.futinform.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final UserFavoriteRepository userFavoriteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        // 1. Verifica se o e-mail já está em uso
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        // 2. Verifica se o telefone já está em uso
        if (userRepository.existsByPhone(request.phone())) {
            throw new RuntimeException("Telefone já cadastrado");
        }

        // 3. Busca o time que o usuário escolheu
        Team favoriteTeam = teamRepository.findById(request.favoriteTeamId())
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));

        // 4. Cria o usuário com a senha hasheada (NUNCA salve a senha original)
        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        userRepository.save(user);

        // 5. Cria o registro de time favorito
        UserFavorite favorite = new UserFavorite();
        favorite.setUser(user);
        favorite.setTeam(favoriteTeam);
        userFavoriteRepository.save(favorite);

        // 6. Gera o token JWT
        String token = jwtService.generateToken(user.getId(), user.getEmail());

        return buildAuthResponse(token, user, favoriteTeam);
    }

    public AuthResponse login(LoginRequest request) {

        // 1. Busca o usuário pelo e-mail
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
        // Mensagem genérica propositalmente — não revelamos se o e-mail existe

        // 2. Verifica a senha (BCrypt compara o hash automaticamente)
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        // 3. Busca o time favorito para incluir na resposta
        UserFavorite favorite = userFavoriteRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Time favorito não configurado"));

        // 4. Gera o token JWT
        String token = jwtService.generateToken(user.getId(), user.getEmail());

        return buildAuthResponse(token, user, favorite.getTeam());
    }

    // Monta o objeto de resposta — evita repetição entre register e login
    private AuthResponse buildAuthResponse(String token, User user, Team team) {
        return new AuthResponse(
                token,
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                team.getName(),
                // A liga do time — o Angular usa isso para redirecionar para a tabela certa
                team.getLeague() != null ? team.getLeague().getCode() : null,
                3600000L // 1 hora em milissegundos
        );
    }
}
