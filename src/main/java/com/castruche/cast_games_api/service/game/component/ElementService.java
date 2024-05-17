package com.castruche.cast_games_api.service.game.component;

import com.castruche.cast_games_api.dao.game.component.ElementRepository;
import com.castruche.cast_games_api.dto.game.component.ElementDto;
import com.castruche.cast_games_api.entity.game.component.Element;
import com.castruche.cast_games_api.formatter.game.component.ElementFormatter;
import com.castruche.cast_games_api.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ElementService extends GenericService<Element, ElementDto> {

    private static final Logger logger = LogManager.getLogger(ElementService.class);
    private ElementRepository elementRepository;
    private ElementFormatter elementFormatter;

    public ElementService(ElementRepository elementRepository, ElementFormatter elementFormatter) {
        super(elementRepository, elementFormatter);
        this.elementRepository = elementRepository;
        this.elementFormatter = elementFormatter;
    }
}
