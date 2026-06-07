package perondi.futinform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perondi.futinform.entity.UserFavorite;

import java.util.Optional;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {

    Optional<UserFavorite> findByUserId(String userId);

    java.util.List<UserFavorite> findByTeamId(Long teamId);
}
