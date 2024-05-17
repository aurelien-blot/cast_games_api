package com.castruche.cast_games_api.service.game.component;

import com.castruche.cast_games_api.dao.game.component.ElementTypeRepository;
import com.castruche.cast_games_api.dto.game.component.ElementTypeDto;
import com.castruche.cast_games_api.entity.game.component.ElementType;
import com.castruche.cast_games_api.formatter.game.component.ElementTypeFormatter;
import com.castruche.cast_games_api.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ElementTypeService extends GenericService<ElementType, ElementTypeDto> {

    private static final Logger logger = LogManager.getLogger(ElementTypeService.class);
    private ElementTypeRepository elementTypeRepository;
    private ElementTypeFormatter elementTypeFormatter;

    public ElementTypeService(ElementTypeRepository elementTypeRepository, ElementTypeFormatter elementTypeFormatter) {
        super(elementTypeRepository, elementTypeFormatter);
        this.elementTypeRepository = elementTypeRepository;
        this.elementTypeFormatter = elementTypeFormatter;
    }
}
