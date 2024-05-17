package com.castruche.cast_games_api.dao.game;

import com.castruche.cast_games_api.entity.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {


}
