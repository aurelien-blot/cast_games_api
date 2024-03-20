package com.castruche.cast_games_api.dao.util;

import com.castruche.cast_games_api.entity.util.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

    Setting findByShortName(String shortName);
}
