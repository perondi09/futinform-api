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

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        if (userRepository.existsByPhone(request.phone())) {
            throw new RuntimeException("Telefone já cadastrado");
        }

        Team favoriteTeam = teamRepository.findById(request.favoriteTeamId())
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));

        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        userRepository.save(user);

        UserFavorite favorite = new UserFavorite();
        favorite.setUser(user);
        favorite.setTeam(favoriteTeam);
        userFavoriteRepository.save(favorite);

        String token = jwtService.generateToken(user.getId(), user.getEmail());

        return buildAuthResponse(token, user, favoriteTeam);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        UserFavorite favorite = userFavoriteRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Time favorito não configurado"));

        String token = jwtService.generateToken(user.getId(), user.getEmail());

        return buildAuthResponse(token, user, favorite.getTeam());
    }

    private AuthResponse buildAuthResponse(String token, User user, Team team) {
        return new AuthResponse(
                token,
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                team.getName(),
                team.getLeague() != null ? team.getLeague().getCode() : null,
                3600000L
        );
    }
}
