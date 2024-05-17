package com.castruche.cast_games_api.controller.game.component;

import com.castruche.cast_games_api.dto.game.component.ColorDto;
import com.castruche.cast_games_api.service.game.component.ColorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.castruche.cast_games_api.controller.ConstantUrl.ELEMENT_COLOR;

@RestController
@RequestMapping(ELEMENT_COLOR)
public class ColorController {

    private final ColorService colorService;
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping()
    public List<ColorDto> getAll() {
        return colorService.getAllDto();
    }

}
