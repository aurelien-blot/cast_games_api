package com.castruche.cast_games_api.dao.game.component;

import com.castruche.cast_games_api.entity.game.component.ElementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementTypeRepository extends JpaRepository<ElementType, Long> {


}
