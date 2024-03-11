package com.castruche.cast_games_api.dao;

import com.castruche.cast_games_api.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {


}
