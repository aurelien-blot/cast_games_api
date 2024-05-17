package com.castruche.cast_games_api.service.game.component;

import com.castruche.cast_games_api.dao.game.component.ColorRepository;
import com.castruche.cast_games_api.dto.game.component.ColorDto;
import com.castruche.cast_games_api.entity.game.component.Color;
import com.castruche.cast_games_api.formatter.game.component.ColorFormatter;
import com.castruche.cast_games_api.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ColorService extends GenericService<Color, ColorDto> {

    private static final Logger logger = LogManager.getLogger(ColorService.class);
    private ColorRepository colorRepository;
    private ColorFormatter colorFormatter;

    public ColorService(ColorRepository colorRepository, ColorFormatter colorFormatter) {
        super(colorRepository, colorFormatter);
        this.colorRepository = colorRepository;
        this.colorFormatter = colorFormatter;
    }
}
