package com.castruche.cast_games_api.dao;

import com.castruche.cast_games_api.entity.Player;
import com.castruche.cast_games_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
