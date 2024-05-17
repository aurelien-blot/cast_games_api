package com.castruche.cast_games_api.controller.game.elementType;

import com.castruche.cast_games_api.dto.game.component.ElementTypeDto;
import com.castruche.cast_games_api.service.game.component.ElementTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.castruche.cast_games_api.controller.ConstantUrl.ELEMENT_TYPE;

@RestController
@RequestMapping(ELEMENT_TYPE)
public class ElementTypeController {

    private final ElementTypeService elementTypeService;
    public ElementTypeController(ElementTypeService elementTypeService) {
        this.elementTypeService = elementTypeService;
    }

    @GetMapping()
    public List<ElementTypeDto> getAll() {
        return elementTypeService.getAllDto();
    }

}
