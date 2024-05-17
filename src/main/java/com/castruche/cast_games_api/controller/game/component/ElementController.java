package com.castruche.cast_games_api.controller.game.component;

import com.castruche.cast_games_api.dto.game.component.ElementDto;
import com.castruche.cast_games_api.service.game.component.ElementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.castruche.cast_games_api.controller.ConstantUrl.ELEMENT;

@RestController
@RequestMapping(ELEMENT)
public class ElementController {

    private final ElementService elementService;
    public ElementController(ElementService elementService) {
        this.elementService = elementService;
    }

    @GetMapping()
    public List<ElementDto> getAll() {
        return elementService.getAllDto();
    }

}
