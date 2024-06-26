package com.castruche.cast_games_api.dao;

import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByUserId(Long userId);
    List<Player> findByArchivedIsFalseAndUsernameLikeIgnoreCase(String username);
}
