package perondi.futinform.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import perondi.futinform.dtos.user.UserPatchDTO;
import perondi.futinform.dtos.user.UserRequestDTO;
import perondi.futinform.dtos.user.UserResponseDTO;
import perondi.futinform.dtos.user.TeamResponseDTO;
import perondi.futinform.entities.UserEntity;
import perondi.futinform.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final FootballService footballService;

    public UserResponseDTO create(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + request.getEmail());
        }

        Integer teamId = findTeamIdByName(request.getFavoriteTeamName());

        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setFavoriteTeamId(teamId);

        UserEntity saved = userRepository.save(user);
        log.info("Usuário criado: id={}, email={}", saved.getId(), saved.getEmail());

        return toResponse(saved);
    }

    public UserResponseDTO findById(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
        return toResponse(user);
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponseDTO update(UUID id, UserPatchDTO request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email já cadastrado: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }

        if (request.getFavoriteTeamName() != null && !request.getFavoriteTeamName().isBlank()) {
            Integer teamId = findTeamIdByName(request.getFavoriteTeamName());
            user.setFavoriteTeamId(teamId);
        }

        return toResponse(userRepository.save(user));
    }

    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado: " + id);
        }
        userRepository.deleteById(id);
        log.info("Usuário deletado: id={}", id);
    }

    private UserResponseDTO toResponse(UserEntity user) {
        String teamName = null;

        if (user.getFavoriteTeamId() != null) {
            try {
                TeamResponseDTO team = footballService.getTeamById(user.getFavoriteTeamId());
                if (team != null) {
                    teamName = team.getName();
                }
            } catch (Exception e) {
                log.error("Erro ao buscar detalhes do time na API para o ID: {}", user.getFavoriteTeamId());
            }
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .favoriteTeamId(user.getFavoriteTeamId())
                .favoriteTeamName(teamName)
                .createdAt(user.getCreatedAt())
                .build();
    }

    private Integer findTeamIdByName(String teamName) {
        String searchName = teamName.toLowerCase().trim();

        return footballService.getBrasileiraoTeams().getTeams().stream()
                .filter(team ->
                        (team.getName() != null && team.getName().toLowerCase().contains(searchName)) ||
                                (team.getShortName() != null && team.getShortName().toLowerCase().contains(searchName))
                )
                .map(TeamResponseDTO::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum time encontrado com o termo: " + teamName));
    }
}